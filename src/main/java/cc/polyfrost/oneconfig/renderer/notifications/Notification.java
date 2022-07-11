package cc.polyfrost.oneconfig.renderer.notifications;

public class Notification {
    private final String name, message;
    private final long duration;
    private final boolean animation;
    private final boolean dismissible;

    /**
     * Create a new Notification. Pass this into the {@link NotificationManager#sendNotification(Notification)} to display it.
     * @param name title of the notification
     * @param message message to show on the notification
     * @param duration duration (in ms) of the notification
     * @param dismissible whether the notification can be dismissed by the user
     * @param inAnimation whether the notification should animate in
     */
    public Notification(String name, String message, long duration, boolean dismissible, boolean inAnimation) {
        this.name = name;
        this.message = message;
        this.duration = duration;
        this.dismissible = dismissible;
        this.animation = inAnimation;
    }

    /**
     * Create a new Notification. Pass this into the {@link NotificationManager#sendNotification(Notification)} to display it.
     * @param name title of the notification
     * @param message message to show on the notification
     * @param duration duration (in ms) of the notification
     * @param dismissible whether the notification can be dismissed by the user
     */
    public Notification(String name, String message, long duration, boolean dismissible) {
        this(name, message, duration, dismissible, true);
    }

    /**
     * Create a new Notification, lasting 5 seconds. Pass this into the {@link NotificationManager#sendNotification(Notification)} to display it.
     * @param name title of the notification
     * @param message message to show on the notification
     * @param dismissible whether the notification can be dismissed by the user
     */
    public Notification(String name, String message, boolean dismissible) {
        this(name, message, 5000, dismissible, true);
    }

    /**
     * Draw this notification. Returns false once the drawing is finished.
     * @param vg NanoVG context
     */
    public boolean draw(long vg) {
        return true;
    }

    /** Return the length (in ms) that this notification is displayed for. */
    public long getDuration() {
        return duration;
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    /** Return weather this notification can be dismissed by the user. */
    public boolean isDismissible() {
        return dismissible;
    }
}
