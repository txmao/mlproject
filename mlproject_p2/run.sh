#!/bin/sh

# get current directory
cdir=$(pwd)

# Please Modify Your Choosen Argument :
L=30
K=20
tr_set=/home/mdy/workspace/mlproject_p1/dataset/hd_train.csv
vl_set=/home/mdy/workspace/mlproject_p1/dataset/hd_vali.csv
te_set=/home/mdy/workspace/mlproject_p1/dataset/hd_test.csv
is_p=yes

cd $cdir/src
python test.py $L $K $tr_set $vl_set $te_set $is_p
rm *.pyc