package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.DepreciationAggregator;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(description = "Makes a player operator", usage = "/<command> <playername>")
public class Command_op extends FreedomCommand
{

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
        {
            return false;
        }

        if (args[0].equalsIgnoreCase("all") || args[0].equalsIgnoreCase("everyone"))
        {
            msg("Correct usage: /opall");
            return true;
        }
        if (args[0].equalsIgnoreCase("custom"))
        {
            if(args.length < 3)
            {
                msg("Usage: /op custom <player> <message>");
                return true;
            }
            else
            {
                String message = null;
                OfflinePlayer target = null;
                if (args.length >= 3)
                {
                    message = StringUtils.join(ArrayUtils.subarray(args, 2, args.length), " ");
                }
            for (Player onlineTarget : server.getOnlinePlayers())
            {
                if (args[1].equalsIgnoreCase(onlineTarget.getName()))
                {
                    target = onlineTarget;
                }
            }
            if (target == null)
            {
                if (plugin.al.isAdmin(sender) || senderIsConsole)
                {
                    target = DepreciationAggregator.getOfflinePlayer(server, args[0]);
                }
                else
                {
                    msg("That player is not online.");
                    msg("You don't have permissions to OP offline players.", ChatColor.YELLOW);
                    return true;
                }
            }
                if(message.contains("owner") || message.contains("superadmin") || message.contains("senioradmin") || message.contains("telnetadmin") || message.contains("senior") || message.contains("telnet") || message.contains("developer") || message.contains("founder") || message.contains("system") || message.contains("systemadmin")|| message.contains("sysadmin"))
                {
                    msg("You used a forbidden word.");
                    return true;
                }
                FUtil.adminAction(sender.getName(), message, false);
                target.setOp(true);
                return true;
            }
        }
        OfflinePlayer player = null;
        for (Player onlinePlayer : server.getOnlinePlayers())
        {
            if (args[0].equalsIgnoreCase(onlinePlayer.getName()))
            {
                player = onlinePlayer;
            }
        }

        // if the player is not online
        if (player == null)
        {
            if (plugin.al.isAdmin(sender) || senderIsConsole)
            {
                player = DepreciationAggregator.getOfflinePlayer(server, args[0]);
            }
            else
            {
                msg("That player is not online.");
                msg("You don't have permissions to OP offline players.", ChatColor.YELLOW);
                return true;
            }
        }

        FUtil.adminAction(sender.getName(), "Opping " + player.getName(), false);
        player.setOp(true);

        return true;
    }
}
