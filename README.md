# About
Fork of https://bitbucket.org/droidperception/simplewebcam. This displays the video video of your usb webcam connected to your android device.

# Requirements
1) The kernel is V4L2 enabled... I think this is true since kitkat. You can compile your own kernel with:

 CONFIG_VIDEO_DEV=y

 CONFIG_VIDEO_V4L2_COMMON=y

 CONFIG_VIDEO_MEDIA=y

 CONFIG_USB_VIDEO_CLASS=y

 CONFIG_V4L_USB_DRIVERS=y

 CONFIG_USB_VIDEO_CLASS_INPUT_EVDEV=y

2) USB WebCam is UVC camera, and it supports 640x480 resolution with YUYV format. Tested with ELP-USBFHD01M-L21 (barebones webcam.. I think most should work).

Supported platform : Iconia Tab A500.

3) Your phone should be rooted. There's a command that runs `su -c \"chmod 666 <video_loc>\"`, so it needs root permissions. I have superSU installed to manage root permissions.

 This application will also work on V4L2-enabled pandaboard and beagleboard.

4) `ant`, installed, ndk and sdk installed

# Compiling/Installing
	$ cd <project-location>
	$ <path-to-ndk>/ndk-build NDK_PROJECT_PATH=.
	$ <path-to-sdk>/tools/android update project --path . --target android-19 # Optional, if you want a new build.xml and local.properties
	$ ant debug
	$ ~/Library/Android/sdk/platform-tools/adb install -r bin/Main-debug.apk

If this doesn't work, make sure you are reading from the correct video device. The code expects you're reading from `/dev/video4`. You may want to change this to `/dev/video0`. Check out the comments in `CameraPreview.java` for

		private int cameraId=0;
		private int cameraBase=4;
