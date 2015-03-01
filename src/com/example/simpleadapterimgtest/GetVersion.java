package com.example.simpleadapterimgtest;

import android.os.StrictMode;

public class GetVersion {
	public static String GetSystemVersion()
	{
		return android.os.Build.VERSION.RELEASE;
	}

	public static void isHighVerForWebservice(){
		String strVer=GetSystemVersion();
		strVer=strVer.substring(0,3).trim();
		float fv=Float.valueOf(strVer);
		if(fv>2.3)
		{
			 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads()
			.detectDiskWrites()
			.detectNetwork() 
			.penaltyLog() 
			.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
			.detectLeakedSqlLiteObjects() 
			.penaltyLog() 
			.penaltyDeath()
			.build());
		}
		
	}
}