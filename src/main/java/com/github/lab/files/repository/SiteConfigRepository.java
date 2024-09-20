package com.github.lab.files.repository;

import com.github.lab.files.model.db.SiteConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteConfigRepository
		extends JpaRepository<SiteConfig, String>
{

}
