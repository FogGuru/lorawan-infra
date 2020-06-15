kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta1/aio/deploy/recommended.yaml

kubectl proxy &

kubectl apply -f admin-role-binding.yml

kubectl apply -f dashboard-adminuser.yml

kubectl -n kube-system describe secret $(kubectl -n kube-system get secret | grep admin-user | awk '{print $1}')



