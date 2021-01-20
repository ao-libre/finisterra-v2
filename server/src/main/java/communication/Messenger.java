package communication;

public interface Messenger {

    int register(String id);

    void receive(String id, Object object);

    void send(int id, Object object);

}
