
## 
pacman -S --needed --noconfirm make python python3-setuptools

if [[ ! -z $(which pip) ]]
then

	pip install sphinx

	## https://github.com/snide/sphinx_rtd_theme
	pip install sphinx_rtd_theme

elif [[ ! -z $(which easy_install) ]]
then

	easy_install sphinx
	easy_install sphinx_rtd_theme
fi




