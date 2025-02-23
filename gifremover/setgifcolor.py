from PIL import Image
import sys

def process_image(filename, color_depth, color_array, cutoff):
    original = Image.open(filename)

    new = []
    for frame_num in range(original.n_frames):
        original.seek(frame_num)
        new_frame = Image.new('RGBA', original.size)
        new_frame.paste(original)
        pixels = new_frame.load()
        for x in range(0,new_frame.size[0]):
            for y in range(0,new_frame.size[1]):
                currframe = pixels[x,y]
                if(currframe[0]*color_array[0] < cutoff and currframe[1]*color_array[1] < cutoff and currframe[2]*color_array[2] < cutoff):
                    pixels[x,y] = (0,0,0,255)
                else:
                    pixels[x,y] = (int(currframe[0]*color_array[0]),int(currframe[1]*color_array[1]),int(currframe[2]*color_array[2]),255)
        new_frame = new_frame.convert(mode='P', palette=Image.ADAPTIVE, colors=color_depth)
        new.append(new_frame)
    print(original.info["duration"])
    new[0].save('new.gif', append_images=new[1:], save_all=True, duration=original.info["duration"], loop=0)


if __name__ == '__main__':
    if(len(sys.argv) < 2):
        print("This tool changes a gif by a set RGB value and remakes it.\nUSAGE: setgifcolor.py (FILE) -c<r,g,b> -p<int> -k<int>")
        print("\t-c - an array of fractional rgb to modify. Default -c0,0,255")
        print("\t-p - The number of colors to reduce the palette to. Default -p4")
        print("\t-k - The cutoff brightness value that will be rounded down to black. Default -k20")
        print("\tRemember not to have spaces in arguments (because im lazy)")
        quit()
    color_array = [0,0,1.0]
    cutoff = 20
    palette = 4
    for i in range(2,len(sys.argv)):
        if sys.argv[i].startswith("-c"):
            arraystring = sys.argv[i].replace("-c","")
            stringarray = arraystring.split(",")
            color_array = [(float(string)/255.0) for string in stringarray]
            print(color_array)
        if sys.argv[i].startswith("-p"):
            arraystring = sys.argv[i].replace("-p","")
            palette = int(arraystring)
            print(palette)
        if sys.argv[i].startswith("-k"):
            arraystring = sys.argv[i].replace("-k","")
            cutoff = int(arraystring)
            print(cutoff)


    process_image(sys.argv[1], palette, color_array,cutoff)
