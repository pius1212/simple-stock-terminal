package stock.alpaca;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImCondition;
import org.ice1000.jimgui.util.JniLoader;
import org.json.JSONArray;
import stock.etf.etf;
import stock.etf.newsETF;
import stock.stockListener;
import stock.getStockData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

public class alpacaMain {
    static List<etf> etfList = new ArrayList<>();
    static String newsData;
    static boolean weekend = false;

    //main goal is to transcribe the SPYImGui into an alpaca port
    //remove some data sources of finnhub
    //add barchart


    public static void main(String... args) throws IOException, InterruptedException, URISyntaxException {
        System.out.println("______________________________________________________");

        String token = "bqk6knnrh5r9t8htfof0";

        JniLoader.load();

        try (JImGui imGui = new JImGui()) {
            Timer timer = new Timer();
            if (!weekend) {
                imGui.setBackground(JImVec4.fromHSV(0f, 0f, 0f));
                newsETF.updateNews(token);

                for (etf s : etfList) {
                    try {
                        s.update();
                        Thread.sleep(1500);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(1000);

                for (etf s : etfList) {
                    try {
                        s.updateChart();
                        Thread.sleep(1500);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Thread.sleep(1000);


                timer.schedule(new TimerTask() {
                    public void run() {
                        for (etf s : etfList) {
                            try {
                                s.updateChart();
                                Thread.sleep(1000);
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 0, etfList.size() * 300000);
                Thread.sleep(1000);

                timer.schedule(new TimerTask() {
                    public void run() {
                        for (etf s: etfList) {
                            try {
                                s.setNews(newsETF.getStockNews(s.getTicker(), token));
                                Thread.sleep(1000);
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, 0, 3600000);
                Thread.sleep(1000);

                timer.schedule(new TimerTask() {
                    public void run() {
                        try {
                            newsETF.updateNews(token);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 43200000);
                Thread.sleep(1000);

                timer.schedule(new TimerTask() {
                    public void run() {
                        for (etf s : etfList) {
                            try {
                                s.update();
                                Thread.sleep(2000);
                            } catch (IOException | InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, 0, etfList.size() * 300000);
                Thread.sleep(1000);

                //______________________RUN MAIN GUI_________________________________________________________________________
                imGui.initBeforeMainLoop();
                while (!imGui.windowShouldClose()) {
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);

                    if (formattedDate.equals("16:00:00")) {
                        SoundJLayerXY soundToPlay = new SoundJLayerXY("res/nyse-opening-bell.mp3");

                        soundToPlay.play();
                    }
                    imGui.initNewFrame();

                    for (etf s : etfList) {
                        s.renderPrice(imGui);
                    }


                    newsETF.renderNews(imGui);

                    imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
                    imGui.begin("Time");
                    imGui.setWindowFontScale(2.0f);
                    imGui.textColored(JImVec4.fromHSV(4 / 7.0f, 0.8f, 0.8f), formattedDate);
                    imGui.end();


                    imGui.render();
                }
            }
        }
    }
    private static void addETF(String ticker, String token, String resolution) throws IOException, InterruptedException {
        String jsonData = "";

        String x = "";
        String a = "";

        String[] y = null;
        String[] c = null;


        Thread.sleep(1000);

        jsonData = getStockData.getCandle(ticker, token, 1400, "close", resolution);

        x = getStockData.getQuote(ticker, token);
        y = x.split(",", -1);

        Thread.sleep(1000);

        a = getStockData.dayHighLow(ticker, token);
        c = a.split(",", -1);


        Thread.sleep(1000);
        JSONArray obj = new JSONArray(jsonData);
        float[] z = new float[obj.length()];

        for (int i = 0; i < obj.length(); i++) {
            z[i] = obj.getFloat(i);
        }


        JImVec4 color;

        color = JImVec4.fromHSV(7.0f, 0.8f, 0.8f);

        assert false;
        if (y[2].equals("true"))
            color = JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f);

        etf etf = new etf(ticker, y[0], y[1], color, token, z, c[0], c[1], newsETF.getStockNews(ticker, token), resolution, y[3]);

        etfList.add(etf);
    }
}

class SoundJLayerXY extends PlaybackListener implements Runnable {
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;

    public SoundJLayerXY(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
            String urlAsString =
                    "file:///" +
                            new java.io.File(".").getCanonicalPath() +
                            "/" +
                            this.filePath;

            this.player = new AdvancedPlayer(
                    new java.net.URL(urlAsString).openStream(),
                    javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
            );

            this.player.setPlayBackListener(this);

            this.playerThread = new Thread(this, "AudioPlayerThread");

            this.playerThread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // PlaybackListener members

    public void playbackStarted(PlaybackEvent playbackEvent) {
        System.out.println("playbackStarted()");
    }

    public void playbackFinished(PlaybackEvent playbackEvent) {
        System.out.println("playbackEnded()");
    }

    // Runnable members

    public void run() {
        try {
            this.player.play();
        } catch (javazoom.jl.decoder.JavaLayerException ex) {
            ex.printStackTrace();
        }

    }
}