// DLL.cpp: определяет экспортированные функции для приложения DLL.
//

#include "stdafx.h"
#include "Main.h"
#include <shlobj.h>
#include <stdio.h>
#include <iostream>
#include <string>
using namespace std;
JNIEXPORT jstring JNICALL Java_Main_Find(JNIEnv * env, jclass cls)
{
	string s = "";
	char path[MAX_PATH + 1];
	SHGetSpecialFolderPathA(HWND_DESKTOP, path, CSIDL_ADMINTOOLS, FALSE);
	s += path;
	s += "\n";
	SHGetSpecialFolderPathA(HWND_DESKTOP, path, CSIDL_APPDATA, FALSE);
	s += path;
	s += "\n";
	SHGetSpecialFolderPathA(HWND_DESKTOP, path, CSIDL_COMMON_DOCUMENTS, FALSE);
	s += path;
	s += "\n";
	SHGetSpecialFolderPathA(HWND_DESKTOP, path, CSIDL_DESKTOP, FALSE);
	s += path;
	s += "\n";
	SHGetSpecialFolderPathA(HWND_DESKTOP, path, CSIDL_COMMON_PICTURES, FALSE);
	s += path;
	s += "\n";
	return env->NewStringUTF(s.c_str());
}

