package af.asr.logger;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import af.asr.logger.appender.ConsoleAppender;
import af.asr.logger.appender.FileAppender;
import af.asr.logger.appender.RollingFileAppender;
import af.asr.logger.constant.LogLevel;
import af.asr.logger.constant.LoggerMethod;
import af.asr.logger.exception.ClassNameNotFoundException;
import af.asr.logger.exception.PatternSyntaxException;
import af.asr.logger.exception.logger.EmptyPatternException;
import af.asr.logger.exception.logger.FileNameNotProvided;
import af.asr.logger.exception.logger.ImplementationNotFound;
import af.asr.logger.exception.logger.XMLConfigurationParseException;
import af.asr.logger.exception.spi.Logger;
import af.asr.logger.factory.Logfactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;


public class LogfactoryTest {

    private FileAppender PHOENIXFileAppender;
    private ConsoleAppender PHOENIXConsoleAppender;
    private RollingFileAppender PHOENIXRollingFileAppender;
    private static String FILENAME;
    private static String FILEPATH;
    private File consoleAppenderFile;
    private File fileAppenderFile;
    private File rollingFileAppenderFile;

    @BeforeClass
    public static void preSetUp() throws IOException {
        FILEPATH = "src/test/resources/test";
        FILENAME = FILEPATH + "/test.txt";
    }

    @Before
    public void setUp() throws IOException {
        PHOENIXFileAppender = new FileAppender();
        PHOENIXConsoleAppender = new ConsoleAppender();
        PHOENIXRollingFileAppender = new RollingFileAppender();

        consoleAppenderFile = new ClassPathResource("/consoleappender.xml").getFile();
        fileAppenderFile = new ClassPathResource("/fileappender.xml").getFile();
        rollingFileAppenderFile = new ClassPathResource("/rollingfileappender.xml").getFile();
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        Logfactory.stopAll();
        Files.walk(new File("src/test/resources/test").toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testgetDefaultConsoleLoggerClazz() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        assertThat(Logfactory.getDefaultConsoleLogger(PHOENIXConsoleAppender, LogfactoryTest.class), isA(Logger.class));
    }

    @Test
    public void testgetDefaultConsoleLoggerName() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        assertThat(Logfactory.getDefaultConsoleLogger(PHOENIXConsoleAppender, "LogfactoryTest"), isA(Logger.class));

    }

    @Test
    public void testgetDefaultConsoleLoggerLogLevelClazz() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        assertThat(Logfactory.getDefaultConsoleLogger(PHOENIXConsoleAppender, LogfactoryTest.class, LogLevel.DEBUG),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultConsoleLoggerLogLevelName() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        assertThat(Logfactory.getDefaultConsoleLogger(PHOENIXConsoleAppender, "LogfactoryTest", LogLevel.DEBUG),
                isA(Logger.class));

    }

    @Test
    public void testgetDefaultConsoleLoggerClazzImplementation() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        assertThat(Logfactory.getConsoleLogger(PHOENIXConsoleAppender, LoggerMethod.PHOENIXLOGBACK, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetDefaultConsoleLoggerClazzImplementationExcepTion() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        Logfactory.getConsoleLogger(PHOENIXConsoleAppender, null, LogfactoryTest.class);
    }

    @Test
    public void testgetDefaultConsoleLoggerNameImplementation() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        assertThat(Logfactory.getConsoleLogger(PHOENIXConsoleAppender, LoggerMethod.PHOENIXLOGBACK, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetDefaultConsoleLoggerNameImplementationExcepTion() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        Logfactory.getConsoleLogger(PHOENIXConsoleAppender, null, "LogfactoryTest");
    }

    @Test(expected = ClassNameNotFoundException.class)
    public void testgetDefaultConsoleLoggerNameWithTargetNameException() {
        PHOENIXConsoleAppender.setAppenderName("testConsoleappender");
        PHOENIXConsoleAppender.setImmediateFlush(true);
        PHOENIXConsoleAppender.setTarget("System.out");
        Logfactory.getDefaultConsoleLogger(PHOENIXConsoleAppender, "");
    }

    @Test
    public void testgetDefaultFileLoggerClassWithoutRolling() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        assertThat(Logfactory.getDefaultFileLogger(PHOENIXFileAppender, LogfactoryTest.class), isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameWithoutRolling() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        assertThat(Logfactory.getDefaultFileLogger(PHOENIXFileAppender, "LogfactoryTest"), isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerClassLogLevelWithoutRolling() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        assertThat(Logfactory.getDefaultFileLogger(PHOENIXFileAppender, LogfactoryTest.class, LogLevel.DEBUG),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameLogLevelWithoutRolling() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        assertThat(Logfactory.getDefaultFileLogger(PHOENIXFileAppender, "LogfactoryTest", LogLevel.DEBUG),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerClassWithoutRollingImplementation() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        assertThat(Logfactory.getFileLogger(PHOENIXFileAppender, LoggerMethod.PHOENIXLOGBACK, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameWithoutRollingImplementation() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        assertThat(Logfactory.getFileLogger(PHOENIXFileAppender, LoggerMethod.PHOENIXLOGBACK, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetDefaultFileLoggerClassWithoutRollingImplementationException() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        Logfactory.getFileLogger(PHOENIXFileAppender, null, LogfactoryTest.class);
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetDefaultFileLoggerNameWithoutRollingImplementationException() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        Logfactory.getFileLogger(PHOENIXFileAppender, null, "LogfactoryTest");
    }

    @Test(expected = ClassNameNotFoundException.class)
    public void testgetDefaultFileLoggerNameWithoutRollingNameException() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(FILENAME);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        Logfactory.getDefaultFileLogger(PHOENIXFileAppender, "");
    }

    @Test(expected = FileNameNotProvided.class)
    public void testgetDefaultFileLoggerNameWithoutRollingFileNullException() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName(null);
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        Logfactory.getDefaultFileLogger(PHOENIXFileAppender, "LogfactoryTest");
    }

    @Test(expected = FileNameNotProvided.class)
    public void testgetDefaultFileLoggerNameWithoutRollingFileEmptyException() {
        PHOENIXFileAppender.setAppenderName("testFileappender");
        PHOENIXFileAppender.setAppend(true);
        PHOENIXFileAppender.setFileName("");
        PHOENIXFileAppender.setImmediateFlush(true);
        PHOENIXFileAppender.setPrudent(false);
        Logfactory.getDefaultFileLogger(PHOENIXFileAppender, "LogfactoryTest");
    }

    @Test
    public void testgetDefaultFileLoggerClazzWithRolling() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        assertThat(Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameWithRolling() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        assertThat(Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerClazzLogLevelWithRolling() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        assertThat(
                Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, LogfactoryTest.class, LogLevel.DEBUG),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameLogLevelWithRolling() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        assertThat(Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest", LogLevel.DEBUG),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerClazzWithRollingImplementation() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        assertThat(Logfactory.getRollingFileLogger(PHOENIXRollingFileAppender, LoggerMethod.PHOENIXLOGBACK,
                LogfactoryTest.class), isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameWithRollingImplementation() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        assertThat(
                Logfactory.getRollingFileLogger(PHOENIXRollingFileAppender, LoggerMethod.PHOENIXLOGBACK, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetDefaultFileLoggerClazzWithRollingImplementationException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getRollingFileLogger(PHOENIXRollingFileAppender, null, LogfactoryTest.class);
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetDefaultFileLoggerNameWithRollingImplementationException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getRollingFileLogger(PHOENIXRollingFileAppender, null, "LogfactoryTest");

    }

    @Test
    public void testgetDefaultFileLoggerClazzWithFullRolling() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}-%i.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        assertThat(Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerNameWithFullRolling() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}-%i.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        assertThat(Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = FileNameNotProvided.class)
    public void testgetDefaultFileLoggerNameWithRollingFileNullException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(null);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}-%i.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        Logfactory.getDefaultFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = FileNameNotProvided.class)
    public void testgetDefaultFileLoggerNameWithRollingFileEmptyException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName("");
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/testFileappender-%d{ss}-%i.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        Logfactory.getDefaultFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = EmptyPatternException.class)
    public void testgetDefaultFileLoggerNameWithRollingNullFilePattern() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern(null);
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = EmptyPatternException.class)
    public void testgetDefaultFileLoggerNameWithRollingEmptyFilePattern() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = PatternSyntaxException.class)
    public void testgetDefaultFileLoggerNameWithRollingWrongFilePattern() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern(FILENAME);
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");

        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = PatternSyntaxException.class)
    public void testgetDefaultFileLoggerNameWithRollingWrongFileNamePattern() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        PHOENIXRollingFileAppender.setMaxFileSize("10kb");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = ClassNameNotFoundException.class)
    public void testgetDefaultFileLoggerNameWithRollingClassMissing() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "");
    }

    @Test(expected = IllegalStateException.class)
    public void testgetDefaultFileLoggerNameWithRollingIllegalState() {
        PHOENIXRollingFileAppender.setAppenderName("testRollingFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{aaaa}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = FileNameNotProvided.class)
    public void testgetDefaultFileLoggerNameWithRollingNullConstraintsException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(null);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = FileNameNotProvided.class)
    public void testgetDefaultFileLoggerNameWithRollingEmptyConstraintsException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName("");
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = PatternSyntaxException.class)
    public void testgetDefaultFileLoggerNameWithRollingNotIConstraintsException() {
        PHOENIXRollingFileAppender.setAppenderName("testFileRollingappender");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{ss}-%i.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("100KB");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testgetDefaultFileLoggerNameWithRollingIllegalArgumentException() {
        PHOENIXRollingFileAppender.setAppenderName("testRollingFileappenderIllegalArgumentException");
        PHOENIXRollingFileAppender.setAppend(true);
        PHOENIXRollingFileAppender.setFileName(FILENAME);
        PHOENIXRollingFileAppender.setImmediateFlush(true);
        PHOENIXRollingFileAppender.setPrudent(false);
        PHOENIXRollingFileAppender.setFileNamePattern("src/test/resources/test/test-%d{ss}.txt");
        PHOENIXRollingFileAppender.setMaxHistory(5);
        PHOENIXRollingFileAppender.setTotalCap("aaaaaaaaaaa");
        Logfactory.getDefaultRollingFileLogger(PHOENIXRollingFileAppender, "LogfactoryTest");
    }

    @Test
    public void testgetFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getConsoleLogger(consoleAppenderFile, LoggerMethod.PHOENIXLOGBACK, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetFileConsoleLoggerClazzImplementationExcepTion() {
        Logfactory.getConsoleLogger(consoleAppenderFile, null, LogfactoryTest.class);
    }

    @Test
    public void testgetFileLoggerClazzImplementation() {
        assertThat(Logfactory.getFileLogger(fileAppenderFile, LoggerMethod.PHOENIXLOGBACK, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetFileLoggerClazzImplementationExcepTion() {
        Logfactory.getFileLogger(fileAppenderFile, null, LogfactoryTest.class);
    }

    @Test
    public void testgetRollingFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getRollingFileLogger(rollingFileAppenderFile, LoggerMethod.PHOENIXLOGBACK,
                LogfactoryTest.class), isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetRollingFileConsoleLoggerClazzImplementationExcepTion() {
        Logfactory.getRollingFileLogger(rollingFileAppenderFile, null, LogfactoryTest.class);
    }

    @Test
    public void testgetNameFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getConsoleLogger(consoleAppenderFile, LoggerMethod.PHOENIXLOGBACK, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetNameFileConsoleLoggerClazzImplementationExcepTion() {
        Logfactory.getConsoleLogger(consoleAppenderFile, null, "LogfactoryTest");
    }

    @Test
    public void testgetNameFileLoggerClazzImplementation() {
        assertThat(Logfactory.getFileLogger(fileAppenderFile, LoggerMethod.PHOENIXLOGBACK, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetNameFileLoggerClazzImplementationExcepTion() {
        Logfactory.getFileLogger(fileAppenderFile, null, "LogfactoryTest");
    }

    @Test
    public void testgetNameRollingFileConsoleLoggerClazzImplementation() {
        assertThat(
                Logfactory.getRollingFileLogger(rollingFileAppenderFile, LoggerMethod.PHOENIXLOGBACK, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = ImplementationNotFound.class)
    public void testgetNameRollingFileConsoleLoggerClazzImplementationExcepTion() {
        Logfactory.getRollingFileLogger(rollingFileAppenderFile, null, "LogfactoryTest");
    }

    @Test
    public void testgetDefaultFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getDefaultConsoleLogger(consoleAppenderFile, LogfactoryTest.class), isA(Logger.class));
    }

    @Test
    public void testgetDefaultFileLoggerClazzImplementation() {
        assertThat(Logfactory.getDefaultFileLogger(fileAppenderFile, LogfactoryTest.class), isA(Logger.class));
    }

    @Test
    public void testgetDefaultRollingFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getDefaultRollingFileLogger(rollingFileAppenderFile, LogfactoryTest.class),
                isA(Logger.class));
    }

    @Test
    public void testgetNameDefaultFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getDefaultConsoleLogger(consoleAppenderFile, "LogfactoryTest"), isA(Logger.class));
    }

    @Test
    public void testgetNameDefaultFileLoggerClazzImplementation() {
        assertThat(Logfactory.getDefaultFileLogger(fileAppenderFile, "LogfactoryTest"), isA(Logger.class));
    }

    @Test
    public void testgetNameDefaultRollingFileConsoleLoggerClazzImplementation() {
        assertThat(Logfactory.getDefaultRollingFileLogger(rollingFileAppenderFile, "LogfactoryTest"),
                isA(Logger.class));
    }

    @Test(expected = XMLConfigurationParseException.class)
    public void testgetNameDefaultRollingFileConsoleLoggerClazzImplementationParseException() throws IOException {
        rollingFileAppenderFile = new ClassPathResource("/rollingfileappenderexception.xml").getFile();
        Logfactory.getDefaultRollingFileLogger(rollingFileAppenderFile, "LogfactoryTest");
    }

}