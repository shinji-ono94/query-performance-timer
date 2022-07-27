# Javaで高精度なタイマーを使う。

Javaで一定時間待ってほしい時は、一般的にThread.Sleepが使われている気がする。    
しかし、Thread.Sleepの精度が悪すぎ([リンク](https://github.com/shinji-ono94/timer-bench))て、高精度なタイミング制御が要求されるシーンでは使い物にならない。
<br />
<br />  
ひょんなことから、高精度なタイマーを作りたい。 そこで、CPU の周波数単位でカウンタしてくれるQueryPerformanceCounterを使って、高精度なタイマー作ってみる。
<br />
<br />
今回は、kernel32のAPIを使用する。
<br />
<br />
# 実装
以下サイトを参考に作成中。

- [Windows APIをJavaから呼び出す（JNA）](https://torutk.hatenablog.jp/entry/20121020/p1)
- [「JNA」intとDWORDのマッピングについて](https://a4dosanddos.hatenablog.com/entry/2016/08/16/234704)