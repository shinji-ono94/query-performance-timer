package com.example.qpc;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Union;
import com.sun.jna.Pointer;

public class QPC2 {
	public static Pointer timer = null;
	public static LARGE_INTEGER qpf = new LARGE_INTEGER();

	public interface CLibrary extends Library {
		Pointer CreateWaitableTimer(LPSECURITY_ATTRIBUTES Security, boolean ManualReset, Pointer TimerName);

		boolean SetWaitableTimer(Pointer hTimer, LARGE_INTEGER interval, long lPeriod,
				PTIMERAPCROUTINE pfnCompletionRoutine, Pointer lpArgToCompletionRoutine, boolean fResume);

		String WaitForSingleObject(Pointer hHandle, long dwMilliseconds);

		boolean CloseHandle(Pointer hHandle);

		boolean QueryPerformanceFrequency(LARGE_INTEGER frequency);

		Pointer GetCurrentThread();

		int SetThreadAffinityMask(Pointer hTread, int dwThreadAffinityMask);

		boolean QueryPerformanceCounter(LARGE_INTEGER counter);
	}

	// LARGE_INTEGER（unionのtypedef）のJava対応型定義
	public static class LARGE_INTEGER extends Union {
		public long QuadPart;
	}

	public static class LPSECURITY_ATTRIBUTES extends Union {
		public long nLength;
		public Pointer lpSecurityDescriptor;
		public boolean bInheritHandle;
	}

	public static class PTIMERAPCROUTINE extends Union {
		public Pointer lpArgToCompletionRoutine;
		public long dwTimerLowValue;
		public long dwTimerHighValue;
	}

	public static int wait(int msec) {
		LARGE_INTEGER interval = new LARGE_INTEGER();
		LARGE_INTEGER qpc_before = new LARGE_INTEGER();
		LARGE_INTEGER qpc_after = new LARGE_INTEGER();
		interval.QuadPart = -10 * 1000 * msec; /* unit:100nsec, wait xx msec */

		if (!QPC2.INSTANCE.QueryPerformanceCounter(qpc_before)) {
			return -999;
		}

		if (!QPC2.INSTANCE.SetWaitableTimer(
				timer, // HANDLE hTimer,
				interval, // LARGE_INTEGER *pDueTime,
				0, // LONG lPeriod,
				null, // PTIMERAPCROUTINE pfnCompletionRoutine,
				null, // LPVOID lpArgToCompletionRoutine,
				false // BOOL fResume
		)) {
			return -999;
		}
		if (!QPC2.INSTANCE.WaitForSingleObject(timer, (msec + 100)).equals("WAIT_OBJECT_0")) {
			return -999;
		}
		if (!QPC2.INSTANCE.QueryPerformanceCounter(qpc_after)) {
			return -999;
		}

		return 0;
	}

	public static int precision_check(int N, int msec) {
		LARGE_INTEGER interval = new LARGE_INTEGER();
		LARGE_INTEGER qpc_before = new LARGE_INTEGER();
		LARGE_INTEGER qpc_after = new LARGE_INTEGER();
		interval.QuadPart = -10 * 1000 * msec; /* unit:100nsec, wait xx msec */
		double sum = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE, ave = 0, sd = 0;
		double[] buf = new double[N];

		for (int i = 0; i < N; i++) {
			if (!QPC2.INSTANCE.QueryPerformanceCounter(qpc_before)) {
				return -999;
			}

			if (!QPC2.INSTANCE.SetWaitableTimer(
					timer, // HANDLE hTimer,
					interval, // LARGE_INTEGER *pDueTime,
					0, // LONG lPeriod,
					null, // PTIMERAPCROUTINE pfnCompletionRoutine,
					null, // LPVOID lpArgToCompletionRoutine,
					false // BOOL fResume
			)) {
				return -999;
			}
			if (!QPC2.INSTANCE.WaitForSingleObject(timer, (msec + 100)).equals("WAIT_OBJECT_0")) {
				return -999;
			}
			if (!QPC2.INSTANCE.QueryPerformanceCounter(qpc_after)) {
				return -999;
			}
			long elapsed_qpc = qpc_after.QuadPart - qpc_before.QuadPart;
			double elapsed_msec = (double) elapsed_qpc * 1000.0 / (double) qpf.QuadPart;
			buf[i] = elapsed_msec;
			sum += elapsed_msec;
			if (max < elapsed_msec)
				max = elapsed_msec;
			if (elapsed_msec < min)
				min = elapsed_msec;
		}
		ave = sum / N;
		for (int i = 0; i < N; i++) {
			sd += Math.pow(ave - buf[i], 2.0);
		}
		sd /= N - 1;
		sd = Math.sqrt(sd);

		System.out.printf("msec=%f, N=%d, ave=%f, sd=%f, max=%f, min=%f\n", (double) msec / 1000.0, N, ave, sd, max,
				min);

		return 0;
	}

	public static void main(String[] args) {
		if (!QPC2.INSTANCE.QueryPerformanceCounter(qpf)) {
			return;
		}

		timer = QPC2.INSTANCE.CreateWaitableTimer(
				null,
				true,
				null);
		if (timer == null) {
			return;
		}

		wait(100);

		precision_check(100, 1);

		if (!QPC2.INSTANCE.CloseHandle(timer)) {
			return;
		}
	}

	public static CLibrary INSTANCE = (CLibrary) Native.loadLibrary("kernel32", CLibrary.class);
}