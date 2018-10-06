package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.List;


public class DirListener extends Thread {

	public File file;
	public DirListener(File file)
	{
		this.file = file;
	}

	@Override
	public void run()
	{
		for(;;)
			try {
				watchForChanges();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public  void watchForChanges() throws IOException {
		Path path = file.toPath();
		if(file.isDirectory()) {
			WatchService ws = path.getFileSystem().newWatchService();
			path.register(ws, StandardWatchEventKinds.ENTRY_CREATE);
			WatchKey watch = null;
			while(true) {
			try {
				watch = ws.take();
			} catch (InterruptedException e) {
				System.err.println("Interrupted");
			}
			List<WatchEvent<?>> events = watch.pollEvents();
			watch.reset();
			for(WatchEvent<?> event: events) {
				Kind<Path> kind = (Kind<Path>) event.kind();
				Path context = (Path) event.context();
				if(kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
					if(extension(context.getFileName().toString()).equals("apk")) {
						System.out.println(".apk file created. parsing...");
						BasicServer.incrUnackedApks();
						startNewThread(context);
					}
					else {
						System.out.println("file created. illegal filetype. file must be of type .apk");
						continue;
				}
					
				}
				
				
			}
			
		}
		}
		else {
			System.out.println("argument is not a directory. exiting program.");
			System.exit(1);
		}
	}
	
	private void startNewThread(Path context) {
		String path = this.file.getPath() + '/' + context.getFileName();
		new ApkHandler(path).start();
		
	}

	private static String extension(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
}
