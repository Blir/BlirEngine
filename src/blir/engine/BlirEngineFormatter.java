package blir.engine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * The formatter BlirEngine uses for its loggers. Arguments are formatted with
 * String.format(), as opposed to the standard Logger format. For help with date
 * formats, @see
 * http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
 *
 * @author Blir
 */
public class BlirEngineFormatter extends Formatter {

    private final DateFormat dateFormat;
    private final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Create a new BlirEngineFormatter.
     *
     * @param format the format with which to represent the date.
     */
    public BlirEngineFormatter(String format) {
        dateFormat = new SimpleDateFormat(format);
    }

    @Override
    public String format(LogRecord record) {

        StringBuilder sb = new StringBuilder();

        sb
                .append(dateFormat.format(record.getMillis()))
                .append(" [")
                .append(record.getLevel())
                .append("] ");

        String msg = record.getMessage();

        if (msg != null) {
            Object[] parameters = record.getParameters();
            if (parameters != null) {
                msg = String.format(msg, parameters);
            }
            sb.append(msg);
        }

        Throwable thrown = record.getThrown();
        if (thrown != null) {
            PrintWriter pw = null;
            try {
                StringWriter sw = new StringWriter();
                pw = new PrintWriter(sw);
                thrown.printStackTrace(pw);

                sb
                        .append(LINE_SEPARATOR)
                        .append(sw.toString());
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
        }

        return sb.append(LINE_SEPARATOR).toString();
    }
}
