import numpy as np
import pyopencl as cl
import time

if __name__ == '__main__':

    # stvaranje konteksta i dohvacanje informacija o napravi
    ctx = cl.create_some_context(interactive=False)
    dev = ctx.get_info(cl.context_info.DEVICES)[0]
    L_multiple = dev.get_info(cl.device_info.PREFERRED_WORK_GROUP_SIZE_MULTIPLE)
    G_max = dev.get_info(cl.device_info.MAX_WORK_ITEM_SIZES)[0]
    
    # podesi parametre
    N = 2**24
    G = G_max // (2**0)
    L = 1 #L_multiple * 1
    print(f"N = {N}\nG = {G}\nL = {L}")
    

    # ucitaj program
    src = None
    with open("primes.cl", "r") as f:
        src = f.read()
    

    # pripremi podatke
    data = np.arange(start=1, stop=N+1, dtype=np.int32)
    result = 0
    

    # stvori red
    queue = cl.CommandQueue(ctx)


    # pripremi memoriju naprave
    dev_data = cl.Buffer(context=ctx, flags=cl.mem_flags.READ_ONLY | cl.mem_flags.COPY_HOST_PTR, hostbuf=data)
    dev_count = cl.Buffer(context=ctx, flags=cl.mem_flags.READ_WRITE, size=4)


    # prevedi i izvrsi program
    prog = cl.Program(ctx, src).build()
    start_time = time.time()
    evt = prog.count_primes(queue, (G, ), (L, ), dev_data, dev_count, np.int32(N//G))
    evt.wait()
    duration = time.time() - start_time
    count = np.empty(1, dtype=np.int32)
    cl.enqueue_copy(queue, count, dev_count)
    print(f"Dovrseno u {duration} sekundi.\nProstih brojeva: {count[0]}")