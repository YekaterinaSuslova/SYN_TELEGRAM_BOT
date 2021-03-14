package telegram_bot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SolveCommand extends BotCommand {

    public SolveCommand() {
        super("solve", "решить");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

       String chatId = chat.getId().toString();

        if (arguments != null && arguments.length == 3) {
            SendMessage answer = new SendMessage();
            answer.setChatId(chatId);
            answer.setParseMode(ParseMode.MARKDOWN);
            try {
                StringBuilder messageTextBuilder = new StringBuilder("`x = ");

                double x = Double.parseDouble(arguments[0]);
                messageTextBuilder.append(arguments[0]).append("`\n");

                double a = Double.parseDouble(arguments[1]);
                messageTextBuilder.append("`a = ").append(arguments[1]).append("`\n");

                double b = Double.parseDouble(arguments[2]);
                messageTextBuilder.append("`b = ").append(arguments[2]).append("`\n\n");

                String y = solve(x, a, b);
                messageTextBuilder.append("*y = ").append(y).append("*");
                answer.setText(messageTextBuilder.toString());

             } catch (Exception e) {
                answer.setText("*Ошибка в введенных аргументах.*");
            }
            
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        } else {
            SendPhoto answer = MyTelegramBot.getDefaultMessage(chatId);
            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private String solve(double x, double a, double b) {
        StringBuilder answer = new StringBuilder();
        double y;

        if (x > 5) {
            y = (5 * a * b) / ((x * x) + (a * a));
        } else {
            y = (4 * (a + b - x) * (a + b - x));

        }

        answer.append(y);
        return String.format("%.3f", y);
    }
}
