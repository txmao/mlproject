'''
Created on Jan 25, 2017

@author: mdy
'''

class bTreeNode:
    
    def __init__(self, label=-1, attri=-1, left=None, right=None, isLeaf=-1, inde=-1):
        self.n_label = label # common value
        self.n_attri = attri # attribute, is -1 if it is leaf
        self.n_left = left
        self.n_right = right
        self.n_isLeaf = isLeaf # 1, if is leaf
        self.n_inde = inde # node id
        
    def prtNode(self):
        print '[', self.n_label, self.n_attri, self.n_left, self.n_right, self.n_isLeaf, self.n_inde, ']'
        
        
if __name__=='__main__':
    n = bTreeNode() #default
    n.prtNode()