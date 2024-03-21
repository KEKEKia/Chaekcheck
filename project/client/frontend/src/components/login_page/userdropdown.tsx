import { Fragment } from 'react';
import { Menu, Transition } from '@headlessui/react';
import { useDispatch } from 'react-redux';
import UserIcon from '../../assets/icons/usericon';
import Logoutrepository from '../../repository/auth/authRepository';
import Signoutrepository from '../../repository/auth/memberRepository';
import { useNickname } from '../../data_source/apiInfo';
// import { logout } from '../../store/actions/authActions';

function UserDropdown() {
  const name = useNickname();
  const dispatch = useDispatch();

  // const handleLogout = () => {
  //   dispatch(logout());
  // };

  return (
    <Menu as="div" className="relative w-28 inline-block text-left">
      <div>
        <Menu.Button className="inline-flex w-full justify-center gap-x-1.5 hover:rounded-t-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm hover:ring-1 hover:ring-inset hover:ring-gray-300 hover:bg-gray-50">
          <UserIcon styleString="w-6 h-6" />
          {name}
        </Menu.Button>
      </div>

      <Transition
        as={Fragment}
        enter="transition ease-out duration-100"
        enterFrom="transform opacity-0 scale-95"
        enterTo="transform opacity-100 scale-100"
        leave="transition ease-in duration-75"
        leaveFrom="transform opacity-100 scale-100"
        leaveTo="transform opacity-0 scale-95">
        <Menu.Items className="absolute left-0 right-0 z-10  w-28 origin-top-right rounded-b-md bg-BACKGROUND-50 shadow-lg ring-1 ring-black">
          <div className="py-1">
            <Menu.Item>
              <button
                type="button"
                className="w-full bg-gray-100 text-gray-900 block px-4 py-2 text-sm hover:bg-SECONDARY-100"
                onClick={() => Logoutrepository({ dispatch })}>
                로그아웃
              </button>
            </Menu.Item>
            <Menu.Item>
              <button
                type="button"
                className="w-full bg-gray-100 text-gray-900 block px-4 py-2 text-sm hover:bg-SECONDARY-100"
                onClick={() => Signoutrepository({ dispatch })}>
                회원탈퇴
              </button>
            </Menu.Item>
          </div>
        </Menu.Items>
      </Transition>
    </Menu>
  );
}

export default UserDropdown;
