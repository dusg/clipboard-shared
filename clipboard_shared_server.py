import pyperclip
import pyjsonrpc


class RequestHandler(pyjsonrpc.HttpRequestHandler):
    @pyjsonrpc.rpcmethod
    def get_clipboard(self) -> str:
        return pyperclip.paste()

    @pyjsonrpc.rpcmethod
    def set_clipboard(self, content: str):
        pyperclip.copy(content)


HOST = 'localhost'
if __name__ == '__main__':
    # Threading HTTP-Server
    http_server = pyjsonrpc.ThreadingHttpServer(
        server_address=(HOST, 8088),
        RequestHandlerClass=RequestHandler
    )
    print("Starting HTTP server ...")
    # print("URL: http://localhost:8080")
    http_server.serve_forever()
