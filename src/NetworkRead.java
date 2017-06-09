import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;




class NetworkRead implements Runnable {
    private Socket socket;

    Client client;
    Map map;
    int id;

    public NetworkRead(Map map, Socket socket, Client client) {
        this.map =map;
        this.socket = socket;
        this.client=client;

    }

    public void run() {
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverString;
            while ((serverString = in.readLine()) != null) {
                //System.out.println(serverString);

                if(serverString.startsWith("Wait for start")){
                    String mar[] = serverString.split("##");
                    id = Integer.parseInt(mar[1]);
                    map.id=id;
                    map.frame.setTitle("id = "+id+"Connected to server. Wait for the other dude.");
                }
                else
                if(serverString.startsWith("start")){
                    String mar[] = serverString.split("##");
                    int turn=Integer.parseInt(mar[1]);
                    if (id == turn){
                        map.turn = true;

                        map.frame.setTitle("id="+id+" The game has been started, Play! it's your turn");
                    }
                    else {
                        map.turn = false;
                        map.frame.setTitle("id="+id+" The game has been started,Wait for your turn!");
                    }

                }
                else
                if(serverString.startsWith("turn")){
                    String mar[] = serverString.split("##");
                    int iid = Integer.parseInt(mar[1]);
                    int x = Integer.parseInt(mar[2]);
                    int y = Integer.parseInt(mar[3]);
                    int button = Integer.parseInt(mar[4]);
                    if (id != iid){

                        map.frame.setTitle("id="+id+"    Play! it's your turn");
                        map.me.update(x, y, button);

                    }
                    else {

                        map.frame.setTitle("id="+id+"     Wait for your turn!");
                    }
                }
                else
                if(serverString.startsWith("Bye")){
                    try {
                        Thread.sleep(4000);
                    }
                    catch(Exception e1){
                        e1.printStackTrace();
                    }
                    break;
                }
                else
                if(serverString.startsWith("end")){
                    String mar[]=serverString.split("##");
                    int wl = Integer.parseInt(mar[1]);
                    int iid = Integer.parseInt(mar[1]);
                    if (id == iid){
                        if(wl == 1){
                            map.turn=false;
                            map.frame.setTitle("id="+id+"    You are the losser!");

                        }
                        else{
                            map.turn=false;
                            map.frame.setTitle("id="+id+"     You are the big winner!");

                        }

                    }
                    else {
                        if(wl == 2){
                            map.turn=false;
                            map.frame.setTitle("id="+id+"    You are the big winner!");
                        }
                        else{
                            map.turn=false;
                            map.frame.setTitle("id="+id+"    You are the losser!");
                        }
                    }
                    map.frame.repaint();
                    try {
                        Thread.sleep(4000);
                    }
                    catch(Exception e1){
                        e1.printStackTrace();
                    }
                    map.client.send("Bye");
                }


                //System.out.println(serverString);          //Server says...

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Disconnected from server");
        System.exit(0);
    }
}