import pyperclip
from rpyc import Service, ThreadedServer


class ClipboardServer(Service):

    def exposed_get_clipboard(self) -> str:
        import subprocess
        p = subprocess.Popen("xclip -o", stdout=subprocess.PIPE, shell=True)
        (output, err) = p.communicate()
        p.wait()
        return str(output, encoding='utf8')
        # return pyperclip.paste()

    def exposed_set_clipboard(self, content: str):
        import os
        print('set clipboard: ' + content)
        with open('/home/dusg/copy.txt', encoding='utf8', mode='w') as fp:
            fp.write(content)
        os.system('xclip -i ~/copy.txt')
        pyperclip.copy(content)


if __name__ == '__main__':
    server = ThreadedServer(ClipboardServer, port=18861)
    server.start()
