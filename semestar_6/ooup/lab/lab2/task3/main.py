def mymax(iterable, key=lambda x:x):
    max = max_key = None
    for element in iterable:
        k = key(element)
        if max_key == None or k > max_key:
            max = element
            max_key = k
            
    return max

def main():

    list_ints = [1, 3, 5, 7, 4, -2, 6, 9, 2, 0]
    max_int = mymax(list_ints)
    min_int = mymax(list_ints, lambda x:-x)
    print("Max: {}\nMin: {}".format(max_int, min_int))
    
    str = "Suncana strana ulice"
    max_char = mymax(str)
    print(max_char)
    
    string_list = [
        "Gle", "malu", "vocku", "poslije", "kise",
        "Puna", "je", "kapi", "pa", "ih", "njise"]
    max_str = mymax(string_list)
    print(max_str)
    
    D = {"burek":8, "buhtla":5, "Krafna":500, "kifla":3}
    dict_key = D.get
    max_val = mymax(D, dict_key)
    print(max_val)
    
    
    osobe = [("Tomislav", "Bjelcic"), ("Zomislav", "Tjelcic"), ("Zomislav", "Bjelcic")]
    posljednji = mymax(osobe)
    print(posljednji)
    

main()