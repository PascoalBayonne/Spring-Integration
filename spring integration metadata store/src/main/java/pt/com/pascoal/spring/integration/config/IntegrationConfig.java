package pt.com.pascoal.spring.integration.config;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.integration.file.filters.*;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.jdbc.metadata.JdbcMetadataStore;
import org.springframework.integration.metadata.ConcurrentMetadataStore;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static pt.com.pascoal.spring.integration.contants.ApplicationConstants.TXT;

@IntegrationComponentScan
@EnableIntegration
@Configuration
public class IntegrationConfig {
private static  final Logger LOGGER = Logger.getLogger(IntegrationConfig.class.getName());
//	@Value("#{'${metadata.dir}'}")
//	private String metadataDir;

	@Value("#{'${polling.dir}'}")
	private String pollingDir;


	@Bean
	protected DirectChannel fileIn() {
		return new DirectChannel();
	}

	@Autowired
	private DataSource dataSource;


    @Bean
    public LockRegistry lockRegistry(LockRepository lockRepository) {
        return new JdbcLockRegistry(lockRepository);
    }

    @Bean
    public DefaultLockRepository lockRepository(DataSource dataSource) {
        return new DefaultLockRepository(dataSource);
    }

    @Bean
    public LockRegistryLeaderInitiator leaderInitiator(LockRegistry lockRegistry) {
        LockRegistryLeaderInitiator lockRegistryLeaderInitiator = new LockRegistryLeaderInitiator(lockRegistry);
        lockRegistryLeaderInitiator.start();
        return lockRegistryLeaderInitiator;
    }


	@Bean
	public MetadataStore metadataStore(DataSource dataSource) {
		return new JdbcMetadataStore(dataSource);
	}

	@Bean
	public static  DataSource vendorDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/MetadataStore");
		dataSource.setUsername("root");
		dataSource.setPassword("root");

		return dataSource;
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
		try (FileSystemPersistentAcceptOnceFileListFilter fileSystemPersistentAcceptOnceFileListFilter = new FileSystemPersistentAcceptOnceFileListFilter(
				(ConcurrentMetadataStore) metadataStore(dataSource), "")) {
			fileSystemPersistentAcceptOnceFileListFilter.setFlushOnUpdate(true);

			JdbcMetadataStore jdbcMetadataStore = new JdbcMetadataStore(dataSource);
			jdbcMetadataStore.afterPropertiesSet();

			return fileSystemPersistentAcceptOnceFileListFilter;
		}
	}

//	private PropertiesPersistingMetadataStore metadataStore() throws Exception {
//		PropertiesPersistingMetadataStore propertiesPersistingMetadataStore = new PropertiesPersistingMetadataStore();
//		propertiesPersistingMetadataStore.setBaseDirectory(metadataDir);
//		propertiesPersistingMetadataStore.afterPropertiesSet();
//		return propertiesPersistingMetadataStore;
//	}

	private List<FileListFilter<File>> getFileListFilterList(final String pattern) throws Exception {
		List<FileListFilter<File>> fileListFilterList = new ArrayList<>();
		fileListFilterList.add(new SimplePatternFileListFilter(pattern));

		fileListFilterList.add(persistentFilter());
		return fileListFilterList;
	}
}
