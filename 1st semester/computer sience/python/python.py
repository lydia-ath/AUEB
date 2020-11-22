#3 Player Games:World Geography
class Player:
    question=["1.The Homolographic projection has the correct representation of:","2.The latitudinal differences in pressure delineate a number of major pressure zones, which correspond with:","3.The hazards of radiation belts include:","4.The great Victoria Desert is located in:","5.The intersecting lines drawn on maps and globes are","6.The light of distant stars is affected by:","7.The landmass of which of the following continents is the least?","8.The habitats valuable for commercially harvested species are called:","9.Which of the following is tropical grassland?","10.The iron and steel industries of which of the following countries are almost fully dependent on imported raw materials?","11.The temperature increases rapidly after:"]
    possiblea=[["a)shape ","b)area ", "c)baring ","d)distance"],["a)zones of climate ","b)zones of oceans ","c)zones of land ","d)zones of cyclonic depressions"],["a)deterioration of electronic circuits ","b)damage of solar cells of spacecraft ","c)adverse effect on living organisms ","d)All of the above "],["a)Canada ","b)West Africa ","c)Australia ","d)Australia"],["a)latitudes ","b)longitudes ","c)geographic grids","d)None of the above"],["a)the earth's atmosphere ","b)interstellar dust ","c)both (a) and (b) ","d)None of the above "],["a)Africa ","b)Asia ","c)Australia ","d)Europe"],["a)coral reefs ","b)sea grass bed ","c)hot spots ","d)None of the above"],["a)Taiga ","b)Savannah ","c)Pampas","d)Prairies"],["a)Britain ","b)Japan ","c)Poland ","d)Germany"],["a)ionosphere ","b)exosphere ", "c)stratosphere ","d) troposphere"]]
    ans=["b","a","d","c","c","c","c","b","b","b","a"]
    def __init__(self,name):
        self.name=name
        self.points=0
        self.help1=True
        self.help2=True
        self.deikth=0
    def check(self):
        j=0
        print(Player.question[self.deikth])
        while(j<=3):
            print(Player.possiblea[self.deikth][j])
            j=j+1
        
        if(self.help1==True or self.help2==True):
            if (self.help1==True):
                print("if you want to skip press 1")
            if (self.help2==True) :
                print("if you want to choose betweet 2 possible answers press 2")
            x=input("if you dont need the hint press 0")
            while(x!="1" and x!="2" and x!="0"):
                print("Your available choices are 0 1 or 2.Try again!")
                x=input("if you dont need the hint press 0")
            return x
        return -1
    def skip(self):
        if(self.help1==True):
            self.help1=False
            self.deikth=self.deikth+1
        else:
            print("Dont try to trick me")
    def half(self):
        if(self.help2==True):
            self.help2=False
            if(Player.ans[self.deikth]=="a"):
                print(Player.question[self.deikth],Player.possiblea[self.deikth][0],Player.possiblea[self.deikth][3])
            elif(Player.ans[self.deikth]=="b"):
                print(Player.question[self.deikth],Player.possiblea[self.deikth][1],Player.possiblea[self.deikth][2])
            elif(Player.ans[self.deikth]=="c"):
                print(Player.question[self.deikth],Player.possiblea[self.deikth][1],Player.possiblea[self.deikth][2])
            else:
                print(Player.question[self.deikth],Player.possiblea[self.deikth][0],Player.possiblea[self.deikth][3])
        else:
            print("Dont try to trick me")
    def checkans(self):
        ap=input(self.name+".Please give me an answer")
        while(ap!="a" and ap!="b" and ap!="c" and ap!="d" and ap!="A" and ap!="B" and ap!="C" and ap!="C"):
            print("Your available choices are A B C or D.Try again!")
            ap=input(self.name+".Please give me an answer")
        if (ap==Player.ans[self.deikth]):
            print("Congratsulation!+10points")
            self.points=self.points+10
        else:
            print("wrong answer:(")
        self.deikth=self.deikth+1
    def bonus(self):
        if (self.help1==True):
            self.points=self.points+5
        if (self.help2==True) :
            self.points=self.points+5
        return self.points
name1=input("give the name of player 1")
p1=Player(name1)
name2=input("give the name of player 2")
p2=Player(name2)
name3=input("give the name of player 3")
p3=Player(name3)
i=0
while(i<10):
    ap1=p1.check() 
    if(ap1=="1"):
        p1.skip()
        ap1=p1.check() 
    elif(ap1=="2"):
        p1.half()
    p1.checkans()
    ap2=p2.check() 
    if(ap2=="1"):
        p2.skip()
        ap2=p2.check()
    elif(ap2=="2"):
        p2.half()
    p2.checkans()
    ap3=p3.check() 
    if(ap3=="1"):
        p3.skip()
        ap3=p3.check()
    elif(ap3=="2"):
        p3.half()
    p3.checkans()
    i=i+1
ListPoints=[p1.bonus(),p2.bonus(),p3.bonus()]
ListName=[name1,name2,name3]
top=1
while(top<=2):
    i=2
    while(i>=top):
        if(ListPoints[i]>ListPoints[i-1]):
            tmp1=ListPoints[i]
            ListPoints[i]=ListPoints[i-1]
            ListPoints[i-1]=tmp1
            tmp2=ListName[i]
            ListName[i]=ListName[i-1]
            ListName[i-1]=tmp2
        i=i-1
    top=top+1

if(ListPoints[0]==ListPoints[1] and ListPoints[1]==ListPoints[2]):
    print("Draw between all players")
elif(ListPoints[0]==ListPoints[1]):
    print("Draw between "+ListName[0]+" and "+ListName[1]+"Second is:"+ListName[2])
elif(ListPoints[1]==ListPoints[2]):
    print("The winner is:"+ListName[0]+"Draw between"+ListName[1]+" "+ListName[2])
else:
    print("The winner is:"+ListName[0]+".Second is:"+ListName[1]+".Third is:"+ListName[2])