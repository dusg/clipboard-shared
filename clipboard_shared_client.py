import os
import rpyc
import sys
import time
import pyperclip

last_clipboard = ""
if __name__ == '__main__':
    client = rpyc.connect('192.168.100.12', port=18861)
    while True:
        txt = client.root.get_clipboard()
        if txt != last_clipboard:
            pyperclip.copy(txt)
            last_clipboard = txt
            print('update local clipboard: ' + txt)

        txt = pyperclip.paste()

        if txt != last_clipboard:
            client.root.set_clipboard(txt)
            last_clipboard = txt
            print('update remote clipboard: ' + txt)

        time.sleep(1)

