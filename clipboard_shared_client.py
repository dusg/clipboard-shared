import os
import rpyc
import sys
import time
import pyperclip

last_clipboard = ""


def do_main():
    global last_clipboard
    client = rpyc.connect('192.168.100.12', port=18861)
    while True:
        try:
            txt = client.root.get_clipboard()
            if txt != last_clipboard:
                pyperclip.copy(txt)
                last_clipboard = txt
                print('update local clipboard: ' + txt)

            txt = pyperclip.paste()

            if len(txt) == 0:
                txt = ' '
            if txt != last_clipboard:
                client.root.set_clipboard(txt)
                last_clipboard = txt
                print('update remote clipboard: ' + txt)

            time.sleep(1)
        except pyperclip.PyperclipException as e:
            print(e)


if __name__ == '__main__':
    try:
        while True:
            do_main()
    except EOFError:
        pass
