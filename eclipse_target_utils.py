import xml.etree.ElementTree as ET
import sys
import re


def main(taget_file: str):
    tree = ET.parse(taget_file)
    root = tree.getroot()
    for location in root.find('locations').findall('location'):
        repos = location.find('repository')
        url = repos.get('location')
        name = re.match(r'https?://[\w.]+/([\w.]+)/.*', url).group(1)
        el = ET.Element('repository')
        ET.SubElement(el, 'id').text = name
        ET.SubElement(el, 'layout').text = 'p2'
        ET.SubElement(el, 'url').text = url
        ET.dump(el)


if __name__ == "__main__":

    main(sys.argv[1])
