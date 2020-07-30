import pyperclip
from rpyc import Service
from rpyc.utils.server import ThreadedServer


class ClipboardServer(Service):

    def exposed_get_clipboard(self) -> str:
        return pyperclip.paste()

    def exposed_set_clipboard(self, content: str):
        pyperclip.copy(content)


if __name__ == '__main__':
    server = ThreadedServer(ClipboardServer, port=18861)
    server.start()