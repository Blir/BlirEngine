package blir.engine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 *
 * @author Blir
 */
public class BlirEngineFormatter extends Formatter {

    private final DateFormat dateFormat;
    private final String LINE_SEPARATOR = System.getProperty("line.separator");

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
