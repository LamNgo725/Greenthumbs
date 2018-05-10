package com.example.j.crop.AppConfigs;

import com.example.j.crop.AppConfigs.AppConfig;

/**
 * Created by J on 3/25/2018.
 */

public class Constants {
    /**
     * This variable controls whether immersive mode is an option in the game.
     * It does not mean that immersive mode is automatically on. It means the the immersiveMode
     * value in UserSettings controls whether it is on. See turnOnImmersiveMode methods.
     */

    public static final boolean IMMERSIVE_MODE_ENABLED = AppConfig.IMMERSIVE_MODE;

    /**
     * The name to use in the Log file.
     */

    public static final String LOG_NAME = "PzGameBoard";

    /**
     * The name to use in the Log file for things related to the UI.
     */

    public static final String LOG_UI = "PzGameBoard UI";

} // end class

