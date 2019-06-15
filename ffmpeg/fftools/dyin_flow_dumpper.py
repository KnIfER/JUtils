
 
 
def response(flow):
    f = open('D:\\flowsheet.txt', 'a')
    #if 'ixigua' in flow.request.url:
    print (flow.request.url)
    #f.write(flow.request.url)
    #f.write("...")

def request(flow):
    f = open('D:\\flowsheet.txt', 'a')
    #f.write("??? "flow.request.url+"\n")
    #f.write("\n")
    urlStr = flow.request.url+''
    if "ixigua" in urlStr:
        f.write(flow.request.url+"\n")
        f.flush()