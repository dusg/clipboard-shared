import sys

# import pyperclip
from PyQt5.QtGui import QClipboard
from rpyc import Service, ThreadedServer
from PyQt5.QtWidgets import QApplication, QWidget


class ClipboardServer(Service):

    def exposed_get_clipboard(self) -> str:
        # import subprocess
        # p = subprocess.Popen("xclip -o", stdout=subprocess.PIPE, shell=True)
        # (output, err) = p.communicate()
        # p.wait()
        text = QApplication.clipboard().text()
        return text
        # return str(output, encoding='utf8')
        # return pyperclip.paste()

    def exposed_set_clipboard(self, content: str):
        import os
        print('set clipboard: ' + content)
        QApplication.clipboard().setText(content, QClipboard.Clipboard)
        # with open('/home/dusg/copy.txt', encoding='utf8', mode='w') as fp:
        #     fp.write(content)
        # os.system('xclip -i ~/copy.txt')
        # pyperclip.copy(content)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    server = ThreadedServer(ClipboardServer, port=18861)
    server.start()
