package gradleProject;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

class SampleUtils {
	void test() throws IOException {

		// フォルダのディレクトリを取得
		final Path OBS_PATH_KEY = Paths.get("c:/obs");

		String ind = "ind";
		String createFile = "create file: ";
		//String createDir= "create dir: ";
		String subFile = "create subfile: ";
		String subDir = "create subDir: ";
		String deleteItem = "delete item: ";

		WatchKey dir;

		// WatchServiceのインスタンスを取得
		WatchService watch = FileSystems.getDefault().newWatchService();
		// フォルダの監視をする
		dir = OBS_PATH_KEY.register(watch, ENTRY_CREATE, ENTRY_DELETE);

		for (;;) {
			try {
				// イベント待機
				WatchKey watchkey = watch.take();
				// イベント情報を取得
				for (WatchEvent<?> event : watchkey.pollEvents()) {
					Kind<?> kind = event.kind();

					WatchEvent<Path> ev = cast(event);
					Path context = ev.context();

					// フォルダ、ファイルが新規作成された場合
					if (kind == ENTRY_CREATE) {
						// 監視フォルダのパスを取得
						Path path = (Path) watchkey.watchable();
						File file = path.resolve(context).toFile();

						// 作成されたものがフォルダの場合
						if (file.isDirectory()) {

							// 監視先の確認
							if (watchkey == dir) {
								// 作成されたフォルダ名を出力
								System.out.println(createFile + context);

								//サブフォルダの監視を登録
								file.toPath().register(watch, ENTRY_CREATE, ENTRY_DELETE);

								// 監視がサブフォルダの場合
							} else {
								// 作成されたファイル名を出力
								System.out.println(subFile + context);
							}
						}
						// 作成されたものがファイルの場合
						else if (file.isFile()) {
							// 作成されたファイル名を出力
							//System.out.println(createDir + context);

							// 監視先の確認
							if (watchkey == dir) {
								// 処理を終了する
								break;

								// 監視がサブフォルダの場合
							} else {
								// 拡張子の確認
								String text = context.toString();
								String per = getExtension(text);
								// 拡張子が「.ind」の場合、監視を終了する
								if (per.equals(ind)) {
									watchkey.cancel();
								} else {
									// 作成されたファイル名を出力
									System.out.println(subDir + context);
								}
							}
						}
					}
					// フォルダ、ファイルが削除された場合
					else if (kind == ENTRY_DELETE) {
						// 削除されたフォルダ名、ファイル名を出力
						System.out.println(deleteItem + context);
					}
				}
				// 監視をリセット
				watchkey.reset();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}
	public static String getExtension(String name) {
		if (name == null) {
			return null;
		}

		// 最後の『 . 』の位置を取得します。
		int per = name.lastIndexOf(".");

		// 『 . 』が存在する場合は、『 . 』以降を返します。
		if (per != -1) {
			return name.substring(per + 1);
		}
		return name;
	}
}