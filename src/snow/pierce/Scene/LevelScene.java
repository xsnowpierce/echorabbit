package snow.pierce.Scene;

import snow.pierce.Renderer.Window;

public class LevelScene extends Scene {

    public LevelScene() {
        System.out.println("Inside level scene");
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;
    }

}
