import java.io.File;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.Pointer;

public class QueryPerformanceCounter {
	public interface CLibrary extends Library {
		boolean QueryPerformanceFrequency(LARGE_INTEGER frequency);
		Pointer GetCurrentThread();
		int SetThreadAffinityMask(Pointer hTread, int dwThreadAffinityMask);
		boolean QueryPerformanceCounter(LARGE_INTEGER counter);
	}

    // LARGE_INTEGER（unionのtypedef）のJava対応型定義
    public static class LARGE_INTEGER extends Union {
        public long QuadPart;
    }
    
    public static void main(String[] args) {
    	long nowTimeTik;
    	long toTimeTik;
    	long ticval;
    	LARGE_INTEGER timerFreq = new LARGE_INTEGER();
    	LARGE_INTEGER currentTime = new LARGE_INTEGER();
    	boolean	bRet;
    	int	oldmask;
    	
    	long msec = 20;	//待ち時間msec
    	
    	oldmask = QueryPerformanceCounter.INSTANCE.SetThreadAffinityMask(QueryPerformanceCounter.INSTANCE.GetCurrentThread(), 1);
    	bRet = QueryPerformanceCounter.INSTANCE.QueryPerformanceFrequency(timerFreq);
    	bRet &= QueryPerformanceCounter.INSTANCE.QueryPerformanceCounter(currentTime);
    	QueryPerformanceCounter.INSTANCE.SetThreadAffinityMask(QueryPerformanceCounter.INSTANCE.GetCurrentThread(), oldmask);
    	if(!bRet) {
			return;
		}
    	ticval = (long)(((double)currentTime.QuadPart / (double)timerFreq.QuadPart) * 1000000.0);	// us単位
//		System.out.println(ticval);
    	nowTimeTik = ticval;	// 1=1us
		
		toTimeTik = msec * 1000;
		toTimeTik += nowTimeTik;
		
		long start = System.nanoTime();
		
		// 15ms超のWait
		if( msec > 15 ){
			while( nowTimeTik < (toTimeTik - 15000) ){
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oldmask = QueryPerformanceCounter.INSTANCE.SetThreadAffinityMask(QueryPerformanceCounter.INSTANCE.GetCurrentThread(), 1);
				bRet = QueryPerformanceCounter.INSTANCE.QueryPerformanceCounter(currentTime);
				QueryPerformanceCounter.INSTANCE.SetThreadAffinityMask(QueryPerformanceCounter.INSTANCE.GetCurrentThread(), oldmask);
		    	if(bRet) {
		    		ticval = (long)(((double)currentTime.QuadPart / (double)timerFreq.QuadPart) * 1000000.0);	// us単位
		    		nowTimeTik = ticval;	// 1=1us
		    	}	
			}
		}
		
		// 15ms以下のWait
		while( nowTimeTik < toTimeTik ){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oldmask = QueryPerformanceCounter.INSTANCE.SetThreadAffinityMask(QueryPerformanceCounter.INSTANCE.GetCurrentThread(), 1);
			bRet = QueryPerformanceCounter.INSTANCE.QueryPerformanceCounter(currentTime);
			QueryPerformanceCounter.INSTANCE.SetThreadAffinityMask(QueryPerformanceCounter.INSTANCE.GetCurrentThread(), oldmask);
	    	if(bRet) {
	    		ticval = (long)(((double)currentTime.QuadPart / (double)timerFreq.QuadPart) * 1000000.0);	// us単位
				nowTimeTik = ticval;	// 1=1us
			}
		}
		
		long end = System.nanoTime();
		System.out.println((end - start));
    }

	public static CLibrary INSTANCE = (CLibrary)Native.loadLibrary("kernel32", CLibrary.class);}