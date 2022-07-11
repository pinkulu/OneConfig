package cc.polyfrost.oneconfig.renderer.notifications;

import cc.polyfrost.oneconfig.renderer.RenderManager;

import java.util.ArrayDeque;
import java.util.Deque;

public class NotificationManager {
    public final NotificationManager INSTANCE = new NotificationManager();
    private final Deque<Notification> notifications = new ArrayDeque<>();
    private int maxNotifications = 3;

    public NotificationManager() {

    }

    public void sendNotification(Notification notification, boolean first) {
        if(first) {
            notifications.addFirst(notification);
        } else {
            notifications.addLast(notification);
        }
    }

    public void sendNotification(Notification notification) {
        sendNotification(notification, false);
    }

    public Deque<Notification> getQueue() {
        return notifications;
    }

    public void remove(Notification notification) {
        notifications.remove(notification);
    }

    public void clear() {
        notifications.clear();
    }

    public void setMaxNotifications(int maxNotifications) {
        this.maxNotifications = maxNotifications;
    }

    public int getMaxNotifications() {
        return maxNotifications;
    }

    /**
     * @deprecated not currently implemented!
     */
    @Deprecated
    protected void render() {
        if(notifications.isEmpty()) {
            return;
        }
        RenderManager.setupAndDraw(vg -> {
            Notification notification = notifications.getFirst();
            if(!notification.draw(vg)) notifications.removeFirst();
        });
    }
}
