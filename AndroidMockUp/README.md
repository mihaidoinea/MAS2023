Reverse eng. steps

adb shell pm list packages | sort

adb shell pm path ro.ase.csie.dma.sharedpreferences

adb pull data/app/ro.ase.csie.dma.sharedpreferences/base.apk

java -jar apktool_2.9.0.jar d CheckList-final.apk

keytool -genkey -v -keystore android.keystore -keyalg RSA -keysize 2048 -validity 10000 -alias androidkey

java -jar apktool_2.9.0.jar b CheckList-final -o new.apk

jarsigner -verbose -keystore android.keystore -storepass keystore_password -keypass key_password new.apk androidkey