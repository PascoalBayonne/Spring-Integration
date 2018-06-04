package com.arpit.spring.integration.config;

import static com.arpit.spring.integration.contants.ApplicationConstants.TXT;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.DirectoryScanner;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.WatchServiceDirectoryScanner;
import org.springframework.integration.file.filters.CompositeFileListFilter;
import org.springframework.integration.file.filters.FileListFilter;
import org.springframework.integration.file.filters.FileSystemPersistentAcceptOnceFileListFilter;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;

@IntegrationComponentScan
@EnableIntegration
@Configuration
public class IntegrationConfig {

	@Value("#{'${metadata.dir}'}")
	private String metadataDir;

	@Value("#{'${polling.dir}'}")
	private String pollingDir;

	@Bean
	protected DirectChannel fileIn() {
		return new DirectChannel();
	}

	@Bean
	@InboundChannelAdapter(value = "fileIn", autoStartup = "true", poller = @Poller(fixedDelay = "500"))
	public MessageSource<File> fileMessageSource() throws Exception {
		FileReadingMessageSource fileReadingMessageSource = new FileReadingMessageSource();
		fileReadingMessageSource.setScanner(dirScanner());
		fileReadingMessageSource.setDirectory(new File(pollingDir));
		return fileReadingMessageSource;
	}

	@Bean
	public DirectoryScanner dirScanner() throws Exception {
		WatchServiceDirectoryScanner watchServiceDirectoryScanner = new WatchServiceDirectoryScanner(
				pollingDir);
		watchServiceDirectoryScanner.setFilter(compositeFilter());
		watchServiceDirectoryScanner.setAutoStartup(true);
		return watchServiceDirectoryScanner;
	}

	@Bean
	public CompositeFileListFilter<File> compositeFilter() throws Exception {
		return new CompositeFileListFilter<>(getFileListFilterList(TXT));
	}

	@Bean
	public FileSystemPersistentAcceptOnceFileListFilter persistentFilter()
			throws Exception {
		FileSystemPersistentAcceptOnceFileListFilter fileSystemPersistentAcceptOnceFileListFilter = new FileSystemPersistentAcceptOnceFileListFilter(
				metadataStore(), "");
		fileSystemPersistentAcceptOnceFileListFilter.setFlushOnUpdate(true);
		return fileSystemPersistentAcceptOnceFileListFilter;
	}

	private PropertiesPersistingMetadataStore metadataStore() throws Exception {
		PropertiesPersistingMetadataStore propertiesPersistingMetadataStore = new PropertiesPersistingMetadataStore();
		propertiesPersistingMetadataStore.setBaseDirectory(metadataDir);
		propertiesPersistingMetadataStore.afterPropertiesSet();
		return propertiesPersistingMetadataStore;
	}

	private List<FileListFilter<File>> getFileListFilterList(final String pattern) throws Exception {
		List<FileListFilter<File>> fileListFilterList = new ArrayList<>();
		fileListFilterList.add(new SimplePatternFileListFilter(pattern));
		fileListFilterList.add(persistentFilter());
		return fileListFilterList;
	}
}
