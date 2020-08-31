port = 22
timeout = 1
user = "dusg"
password = "'"


def test_ip(host) -> bool:
    import paramiko
    print('test ip : ' + host)
    try:
        ssh_client = paramiko.SSHClient()
        ssh_client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh_client.connect(host, 22, user, password, timeout=timeout)
        ssh_client.close()
        return True
    except Exception as e:
        print(e)
        return False


if __name__ == '__main__':
    for i in range(1, 255):
        ip = '10.8.52.%d' % i
        if test_ip(ip):
            print('\nmy host is ' + ip)
            exit(0)

    print('not found my remote host')
