import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

public class Start {
    private static final Logger logger = LogManager.getLogger(Start.class);
    public static void main(String[] args) throws IOException, URISyntaxException {
        logger.info("System: user Started the app");
        var text = new Text();
        System.out.println();
        System.out.println();
        text.printer("Welcome to Nwitter!\n",ConsoleColors.CYAN_BOLD);
        text.printer("Agar dar safhe i listi az twitte ha didid:\n",ConsoleColors.BLACK_BOLD_BRIGHT);

        text.printer("Shomare twitte = Raftan be safheye ferestandeye Twitte",ConsoleColors.BLACK_BOLD_BRIGHT);
        text.printer("*Shomare twitte = Like kardane twitte",ConsoleColors.BLACK_BOLD_BRIGHT);
        text.printer("**Shomare twitte =  didane liste afradi ke twitte ra Like karde and",ConsoleColors.BLACK_BOLD_BRIGHT);

        text.printer("#Shomare twitte = Retwitte kardane twitte",ConsoleColors.BLACK_BOLD_BRIGHT);
        text.printer("##Shomare twitte =  didane liste afradi ke twitte ra Retwitte karde and",ConsoleColors.BLACK_BOLD_BRIGHT);

        text.printer("@Shomare twitte = Comment gozashtan baraye twitte",ConsoleColors.BLACK_BOLD_BRIGHT);
        text.printer("@@Shomare twitte =  didane liste afradi ke baraye afradi ke baraye twitte Comment gozashte and",ConsoleColors.BLACK_BOLD_BRIGHT);

        text.printer("$Shomare twitte = Save kardane twitte",ConsoleColors.BLACK_BOLD_BRIGHT);
        text.printer("%Shomare twitte = Forward kardane twitte\n",ConsoleColors.BLACK_BOLD_BRIGHT);

       new Login(true,args);
    }
}
