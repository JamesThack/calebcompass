package calebcompass.calebcompass.events;

import calebcompass.calebcompass.Cache;
import calebcompass.calebcompass.CalebCompass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Events implements Listener {

    private timeOut timer;

    public Events() {
        this.timer= new timeOut();
//        timer.run();
//        timer.runTaskTimer(Bukkit.getPluginManager().getPlugin("CalebCompass"), 0, 1);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
//        if (timer.getCoolDown() >= 1) {
//            return;
//        }
        Player player = e.getPlayer();
        LocObs locObs = CompassInstance.getInstance().getPlayerLocOb(player);
        Cache cache = Cache.getCache();
        BossBar bar = CompassInstance.getInstance().getBarFromPlayer(player);
        if (bar == null) {
            bar = Bukkit.createBossBar( getMessage(Math.round((player.getLocation().getYaw() + 360) / 9), 365), BarColor.valueOf(cache.getBarCol().toUpperCase()), BarStyle.valueOf(cache.getStyle().toUpperCase()));
            bar.addPlayer(player);
            CompassInstance.getInstance().addBar(bar);
        }
        bar.setVisible(CompassInstance.getInstance().isCompassVisible(player));
        bar.setColor(BarColor.valueOf(cache.getBarCol().toUpperCase()));
        bar.setStyle(BarStyle.valueOf(cache.getStyle().toUpperCase()));
        if (locObs != null && locObs.isTracking()) {
            double dist = locObs.getOrigin().distance(locObs.getCertainEnd());
            double newDist = player.getLocation().distance(locObs.getCertainEnd());
            if (newDist > dist) locObs.setOrigin(player.getLocation());
            double travel = newDist / dist;
            if (travel >= 1) travel = 0.999999;
            bar.setProgress(1 - travel);
            Location origin  = player.getLocation();
            Vector target = locObs.getCertainEnd().toVector() ; //our target location (Point B)
            origin.setDirection(target.subtract(origin.toVector())); //set the origin's direction to be the direction vector between point A and B.
            float yaw = origin.getYaw(); //yay yaw
            bar.setTitle( getMessage(Math.round((player.getLocation().getYaw() + 360) / 9), Math.round(yaw / 9)));
        } else {
            bar.setTitle( getMessage(Math.round((player.getLocation().getYaw() + 360) / 9), 500));
            bar.setProgress(0.99999);
        }


    }

    private String replaceWithProper(String insert) {
        Cache cache = Cache.getCache();
        insert = insert.replace("▚", "§r" +cache.getSouth() + cache.getMid())
                .replace("▛", "§r" + cache.getSouthWest()+ cache.getMid())
                .replace("▜", "§r" +cache.getWest()+ cache.getMid())
                .replace("▝", "§r" +cache.getNorthWest()+ cache.getMid())
                .replace("▞", "§r" +cache.getNorth()+ cache.getMid())
                .replace("▟", "§r" +cache.getNorthEast()+ cache.getMid())
                .replace("▘", "§r" +cache.getEast()+ cache.getMid())
                .replace("▙", "§r" +cache.getSouthEast()+ cache.getMid())
                .replace("▓", cache.getQuestColour() + cache.getQuestSymbol() + cache.getMid());
        return insert;
    }

    private String replaceWithoutColour(String insert) {
        Cache cache = Cache.getCache();
        insert = insert.replace("▚", "S")
                .replace("▛", "SW")
                .replace("▜", "W")
                .replace("▝", "NW")
                .replace("▞", "N")
                .replace("▟", "NE")
                .replace("▘", "E")
                .replace("▙", "SE")
                .replace("▓", cache.getQuestSymbol());
        return insert;
    }

    public String getMessage(int dir, int goDir) {
        if (dir >= 40) dir -= 40;
        String overallMessage = ("▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟");
        if (goDir <= 90) {
            StringBuilder newView = new StringBuilder(overallMessage);
            if (goDir - 30  < 10 && goDir - 30 > 0) newView.setCharAt(goDir - 30, '▓');
            newView.setCharAt(goDir + 10, '▓');
            newView.setCharAt(goDir + 50, '▓');
            overallMessage = newView.toString();
        }
        Cache cache = Cache.getCache();
        String over =  overallMessage.substring(dir, dir+ 21);
        return (cache.getStart() + "§r" + cache.getMid() + replaceWithProper(over.substring(0, 10)) + cache.getPointer() + replaceWithoutColour(String.valueOf(over.charAt(10))) + cache.getMid() + replaceWithProper(over.substring(11)) + "§r" + cache.getEnd()).replace("⬟", cache.getMiddleSymbol());
    }


    public class timeOut extends BukkitRunnable {

        public timeOut() {
            this.coolDown = 4;
        }

        private int coolDown;

        public int getCoolDown() {
            return coolDown;
        }

        public void setTime(int setTime) {
            this.coolDown = setTime;
        }


        @Override
        public void run() {
            coolDown -=1;
            if (coolDown < 0) this.coolDown = 4;
        }
    }
}
