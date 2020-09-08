import sys

import pyperclip
from PyQt5.QtGui import QClipboard
from rpyc import Service, ThreadedServer
from PyQt5.QtWidgets import QApplication, QWidget
from threading import Thread


class ClipboardServer(Service):

    def exposed_get_clipboard(self) -> str:
        import subprocess
        p = subprocess.Popen("xclip -o", stdout=subprocess.PIPE, shell=True)
        (output, err) = p.communicate()
        p.wait()
        # text = QApplication.clipboard().text()
        # return text
        return str(output, encoding='utf8')
        # return pyperclip.paste()

    def exposed_set_clipboard(self, content: str):
        import os
        print('set clipboard: ' + content)
        # QmetaObject
        QApplication.clipboard().setText(content, QClipboard.Clipboard)

        with open('/home/dusg/copy.txt', encoding='utf8', mode='w') as fp:
            fp.write(content)
        os.system('xclip -i ~/copy.txt')
        pyperclip.copy(content)


class MyThread(Thread):
    def run(self):
        server = ThreadedServer(ClipboardServer, port=18861)
        server.start()


if __name__ == '__main__':
    # server = MyThread()
    # server.start()

    app = QApplication(sys.argv)
    # app.exec()

    server = ThreadedServer(ClipboardServer, port=18861)
    server.start()
