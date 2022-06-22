package main.scenes;

import main.Game;
import main.gui.Button;
import main.gui.NumericTextField;
import main.gui.TextField;

public class JoinMenuScene extends Scene {
    private TextField ip;
    private TextField port;

    @Override
    public void init() {
        super.init();
        this.ip = new TextField("localhost", Game.panelSize / 2, Game.panelSize / 2, 200, 50);
        this.port = new NumericTextField(1234, Game.panelSize / 2, Game.panelSize / 2 + 60, 200, 50);
        this.addWidget(this.ip);
        this.addWidget(this.port);
        this.addWidget(new Button("Join", Game.panelSize / 2, Game.panelSize / 2 + 120, 200, 50, (button) -> {
            Game.INSTANCE.startClient(this.ip.getContent(), Integer.getInteger(this.port.getContent()));
            Game.INSTANCE.setScene(new LoadingScene("Waiting for Opponent...", () -> {
                Game.INSTANCE.getConnection().ifPresent((c) -> {
                    Game.INSTANCE.setScene(new GuestMenuScene());
                });
            }));
        }));
    }
}
