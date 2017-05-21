'''
Created on Jan 25, 2017

@author: mdy
'''

import csv

# csv_parser(path) create an instance
class csv_parser:
    
    def __init__ (self, f_path_arg):
        '''
        13
        2
        '''
        self.f_path = f_path_arg
        
        self.set_attr = [] #[XB,XC,...,XU] 1
        self.set_label = [] #[column under class attribute] 3
        self.index_attr = []
        self.index_label = [] #easy access
        self.set_data = [] # data without label 2
        self.index_data = []
        
        self.f_parse() #VIP
        
        
    def f_parse (self):
        #read csv file into rows
        with open(self.f_path) as csv_f: #must use self.f_path
            rd = csv.reader(csv_f)
            rows = [row for row in rd]
            
        csv_f.close()
        
        for ln_cnt in range(len(rows)):
            if ln_cnt == 0:
                self.set_attr = rows[ln_cnt][:len(rows[ln_cnt])-1]
                
            else:
                self.set_data.append( [int(key) for key in rows[ln_cnt][:len(rows[ln_cnt])-1]] )
                self.set_label.append( int( rows[ln_cnt][ len(rows[ln_cnt])-1 ] ) )
                
        self.index_attr = list(range(len(self.set_attr)))   # 1
        self.index_data = list(range(len(self.set_data)))   # 2
        self.index_label = list(range(len(self.set_label))) # 3
        
        
                        
            

if __name__=='__main__':
    f_path_arg = '/home/mdy/Desktop/decisionTree/data_sets2/test_set.csv'
    csv_data = csv_parser(f_path_arg)
    
    print csv_data.set_attr
    print len(csv_data.set_data[0])
    print len(csv_data.set_label)
    print csv_data.index_attr
    print len(csv_data.index_data)
    print len(csv_data.index_label)
    print csv_data.set_data[100][1]-csv_data.set_data[100][2]
    print sum(csv_data.set_label)




