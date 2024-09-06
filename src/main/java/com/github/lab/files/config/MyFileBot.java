package com.github.lab.files.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.github.lab.files.common.ShortIdUtil;
import com.github.lab.files.model.FileInfo;
import com.github.lab.files.repository.FileInfoRepository;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.games.Animation;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.InputStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Libs
 * @date 2024/8/29
 */
@Component
@Slf4j
public class MyFileBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer
{
	@Resource
	private FileInfoRepository fileInfoRepository;

	private final TelegramClient telegramClient;

	private final String botToken;

	private final String name;

	private final String chatId;

	private final String url;

	public MyFileBot(@Value("${telegram.bot.token:}")
	String botToken, @Value("${url:}")
	String url, @Value("${telegram.bot.name:}")
	String name, @Value("${telegram.bot.chatId:}")
	String chatId)
	{
		this.botToken = botToken;
		this.name = name;
		this.chatId = chatId;
		this.url = url;
		OkHttpClient okHttpClient = new OkHttpClient().newBuilder().
				callTimeout(1, TimeUnit.MINUTES).
				readTimeout(1, TimeUnit.MINUTES).
				build();
		telegramClient = new OkHttpTelegramClient(okHttpClient, getBotToken());
	}

	@Override
	public String getBotToken()
	{
		return botToken;
	}

	@Override
	public LongPollingUpdateConsumer getUpdatesConsumer()
	{
		return this;
	}

	@Override
	public void consume(Update update)
	{
		if (update.hasMessage())
		{
			FileInfo fileInfo = handleMessage(update.getMessage());
			long chat_id = update.getMessage().getChatId();
			String link = StrUtil.format("{}/f/{}", URLUtil.normalize(url), fileInfo.getShortId());
			String msgText = StrUtil.format("文件名称：{}\n文件大小：{}\n文件直链\uD83D\uDE80：" +
					"<a href=\"{}\">{}</a>", fileInfo.getName(), fileInfo.getSizeFmt(), link, link);
			SendMessage message = SendMessage // Create a message object
					.builder()
					.chatId(chat_id)
					.text(msgText)
					.parseMode(ParseMode.HTML)
					.replyToMessageId(update.getMessage().getMessageId())
					.build();
			try
			{
				telegramClient.execute(message); // Sending our message object to user
			}
			catch (TelegramApiException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	@AfterBotRegistration
	public void afterRegistration(BotSession botSession)
	{
		log.info("Registered bot running state is: {}", botSession.isRunning());
	}

	public FileInfo handleMessage(Message message)
	{
		FileInfo record = new FileInfo();
		record.setId(IdUtil.fastSimpleUUID());
		record.setUploadTime(new Date());
		if (message.hasDocument())
		{
			Document doc = message.getDocument();
			record.setTgFileId(doc.getFileId());
			record.setTgFileUniqueId(doc.getFileUniqueId());
			record.setName(doc.getFileName());
			record.setMimeType(doc.getMimeType());
			record.setSize(doc.getFileSize());
			record.setSizeFmt(fmtFileSize(record.getSize()));
		}
		else if (message.hasPhoto())
		{
			PhotoSize photoSize = message.getPhoto().get(message.getPhoto().size() - 1);
			GetFile getFile = new GetFile(photoSize.getFileId());
			File tgFile = null;
			try
			{
				tgFile = telegramClient.execute(getFile);
				record.setMimeType(FileUtil.getMimeType(tgFile.getFilePath()));
				record.setName(record.getId() + "." + FileUtil.extName(tgFile.getFilePath()));
			}
			catch (TelegramApiException e)
			{
				record.setTgFileId(null);
				log.error(e.getMessage(), e);
			}
			record.setTgFileId(photoSize.getFileId());
			record.setTgFileUniqueId(photoSize.getFileUniqueId());
			record.setSize(photoSize.getFileSize().longValue());
			record.setSizeFmt(fmtFileSize(record.getSize()));
		}
		else if (message.hasVideo())
		{
			Video v = message.getVideo();
			record.setTgFileId(v.getFileId());
			record.setTgFileUniqueId(v.getFileUniqueId());
			record.setName(v.getFileName());
			record.setMimeType(v.getMimeType());
			record.setSize(v.getFileSize());
			record.setSizeFmt(fmtFileSize(record.getSize()));
		}
		if (message.hasAnimation())
		{
			Animation a = message.getAnimation();
			record.setTgFileId(a.getFileId());
			record.setTgFileUniqueId(a.getFileUniqueId());
			record.setName(a.getFileName());
			record.setMimeType(a.getMimeType());
			record.setSize(a.getFileSize());
			record.setSizeFmt(fmtFileSize(record.getSize()));
		}
		if (StrUtil.isNotBlank(record.getTgFileId()))
		{
			record.setShortId(ShortIdUtil.getShortId(record.getTgFileId()));
			fileInfoRepository.save(record);
		}
		return record;
	}

	public static String fmtFileSize(long size)
	{
		if (size <= 0)
		{
			return "0 B";
		}

		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

		return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
	}

	public InputStream downLoadFile(String fileId)
	{
		try
		{
			GetFile getFile = new GetFile(fileId);
			File tgFile = telegramClient.execute(getFile);
			return telegramClient.downloadFileAsStream(tgFile);
		}
		catch (TelegramApiException e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}

	public FileInfo uploadFile(InputStream inputStream, FileInfo info)
	{
		try
		{
			SendDocument document = SendDocument.builder().chatId(chatId).document(new InputFile(inputStream, info.getName())).build();
			Message message = telegramClient.execute(document);
			return handleMessage(message);
		}
		catch (TelegramApiException e)
		{
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
