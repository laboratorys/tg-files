package com.github.lab.files.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Libs
 * @date 2024/9/10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T>
{
	private Integer pageNo;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalCount;
	private List<T> content;

}
