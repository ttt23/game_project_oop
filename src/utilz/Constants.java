package utilz;

import main.Game;

public class Constants {
	public static class UI{
//		public static class Buttons{
//			public static final int B_WIDTH_DEFAULT = 140;
//			public static final int B_HEIGHT_DEFAULT = 56;
//			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
//			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
//
//		}
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 64;
			public static final int B_HEIGHT_DEFAULT = 16;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * 4 * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * 4 * Game.SCALE);

		}
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);

		}
		public static class URMbuttons{
			public static final int URM_DEFAULTSIZE = 56;
			public static final int URM_SIZE = (int)(URM_DEFAULTSIZE * Game.SCALE);

		}
		public static class VolumeButtons{
			public static final int VOLUME_DEFAULT_WIDTH = 16;
			public static final int VOLUME_DEFAULT_HEIGHT = 64;
			public static final int SLIDER_DEFAULT_WIDTH = 256;
			public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH *Game.SCALE );
			public static final int VOLUME_HEIGHT= (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE);

		}
	}
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

}