import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import icu.jnet.mccreate.McCreate;
import org.jannsen.mcreverse.api.entity.akamai.SensorToken;

import java.util.function.Supplier;


public class Main {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Supplier<SensorToken> supplier = () -> new SensorToken("1,a,Yp5a4oOFzWLezR35IZnkot1CI5pyCyecoeC9zMP5G1ipNlZaoxTnU59eldFH8QBZXyoMjbyOrjosZIw48NbkwfVVxx4kohuLYq7V9IiUGHqk8HcVtHF2v/k71Wyx1KJypLSup5tgLOXYGe/ZWvJlh/f1yRsv8Fefn57PQuQalLA=,ooZdWdWintcT2v9wmImoN9ARPxtPFCWmdzA41Bv6Uv0ilTXNZbuBM5PJ8jFhRMjbzBphgw8SM4fvUVUKavYpOCCo+cid14bE/JdSZ2WU+KRaTWEQMFwyuPJC4pvz9v1xNe3755nRcuHyI56MOl29lhMTNlq3CW5HTkBNSJIjHQs=$BYPDYR/f24JxoOxwLIx7yN3WNRB4xmq7ZYGzDbNPL6DFE1A3pWTVWRZlmLWFq2v61HdgWpt8Igd/v9fOWhk+SR7jm9FMZDegNAHxaJzewOZB02kQRhmXUgPcRm4GqdjWA3YOuzn9aQn+5TyK+P4O54HsmfmY41KscX/vLiYRpbkgS0e94qcDiXHkshJbiZztx/LJN7TR1SGhgg4xFoF7ZG6KicH3VmM9VokOcasALfooTaKWai/CsagFqTMpbBi6OSrUdr1nujobiwLe95AcM8fJ6WESST5zQtiPzNr+WNNi+wGcZqjleYHwiPlpbJW+irpxhHNhhac5Ao9Ils34ZIm+YlaN5BG27jNTsrnoegDST8Opa9GN7oKbbqaVuk16MquJ3i458BCedyD8hvUjqlQWXJWS3qeJz1QI2gM+vV1N0/DMGD5XRCuNGh2vAl+J/pQhbLo9TAKJSY5Ezts72G0tl7D/jf2olPV+1YFtiC8KgRaH0IgY/b4GwqJjEX6iTMC9OUq1EscBhIC1YCP3wb5ohtjjmNmTnSPvFTRk0Q5pkKW5KUhhFIRj1NU5JAlDExQZCFAqLLvoJ5ly7UIFsUUJTyjdEgqQNDLnHCDoAY7C5C7iUTftlLPqm6vGv/LEb4CVnVoBThlwzeicaViLfSvxdEUmobBnAehoDrZ3WuOTsy5xECOuHSBeONFzP/yJJiGG2Cj0RZSbvn9M6PJ90I1fd5hNfefK/oTlDIcWLV5ReisZDZjv3IHwjllK4tcNOEdilzR3y4sbXUzIze7TfbwcoixHyprzsMxngbAJHwUc5Enwqs2UcKGpSSw5DwcS4oRXI6Ru0Ii4IiTSk2EfFtkFPfC9bvbtYDJksHumcx09R4UVzWELodl3QCvQgwqm4qmEXagTvli1D3holyB9kw4NFkVzSh3L71HpHdfGdO/MaWbkZyrc1qqsA+WfYdCQnF55nptR/Es1z53Ezbwc1cRCFCM+6LQdjzSr8NfGTOnYYC9MmEcSoqTFd6dDokrww0vx/U0T4CIaAkKBswBfQziVrojCuZfjXfqvmcuYTh/h998R+Rz19W7hcdrrqmw+d0OyzdZu9Sk3HcTALyxarGJf1vZ6p1F7s3DsxZ5N53tnnDhgVACLVnJ/0aCN9R00HvLwhVI6Wt56rEG4FTBAew==$0,0,0");
        McCreate create = new McCreate("imap.gmx.net", 993, "test@example.org", "123456", supplier);
        System.out.println(gson.toJson(create.register("test@example.org")));
    }
}
