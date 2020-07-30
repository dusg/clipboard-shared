import os
import sys
import time
import pyperclip

last_clipboard = ""
HOST = '192.168.100.12'
if __name__ == '__main__':
    import pyjsonrpc

    http_client = pyjsonrpc.HttpClient(
        url="http://" + HOST + ':8088'
    )
    # http_client.call("add", 1, 2)
    # Result: 3

    # It is also possible to use the *method* name as *attribute* name.
    # print http_client.add(1, 2)
    # client = rpyc.connect('192.168.100.12', port=18861)
    while True:
        txt = http_client.get_clipboard()
        if txt != last_clipboard:
            pyperclip.copy(txt)
            last_clipboard = txt
            print('update local clipboard: ' + txt)

        txt = pyperclip.paste()

        if txt != last_clipboard:
            http_client.set_clipboard(txt)
            last_clipboard = txt
            print('update remote clipboard: ' + txt)

        time.sleep(1)
