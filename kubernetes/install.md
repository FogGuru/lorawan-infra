-- Install Kubernetes and Docker

    0   sudo su -
    1   curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
    2   echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" > /etc/apt/sources.list.d/kubernetes.list
    3   apt-get update
    6   curl -sSL https://get.docker.com | sh
    7   sudo usermod -aG docker guru
    8   apt-get update && apt-get install -y kubeadm
    9   sudo systemctl disable dphys-swapfile.service
    10  sudo dphys-swapfile swapoff && sudo dphys-swapfile uninstall && sudo update-rc.d dphys-swapfile remove

-- Only the master from here

    0  kubeadm init --pod-network-cidr 10.244.0.0/16
    1  mkdir -p $HOME/.kube
    2  sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    3  sudo chown $(id -u):$(id -g) $HOME/.kube/config
    6  kubectl apply -f https://raw.githubusercontent.com/coreos/flannel/master/Documentation/kube-flannel.yml

-- Slave join in the cluster

 - That's produced by the master
 - kubeadm join 192.168.0.175:6443 --token 60ctqq.99zf5qzdo7uzxti5 \
    --discovery-token-ca-cert-hash sha256:ebba5c9b08eb2979687d778e6d0764b19d4ce8e7b13b9a0ca178efc0d56ab191 
 - It has to be run in every slave
