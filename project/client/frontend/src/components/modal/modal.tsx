import { ReactNode, Fragment, useRef } from 'react';
import { Dialog, Transition } from '@headlessui/react';

interface ModalProps {
  closeModal: () => void;
  OpenModal: boolean;
  children: ReactNode;
  width: string;
  height: string;
}

function Modal({
  closeModal,
  OpenModal = false,
  children,
  width,
  height,
}: ModalProps) {
  const cancelButtonRef = useRef(null);

  return (
    <Transition.Root show={OpenModal} as={Fragment}>
      <Dialog as="div" initialFocus={cancelButtonRef} onClose={closeModal}>
        <div className="fixed inset-0 z-10 bg-black bg-opacity-50 backdrop-brightness-50 overflow-y-scroll">
          <div className="flex min-h-full justify-center text-center items-center">
            <Transition.Child
              as={Fragment}
              enter="ease-out duration-300"
              enterFrom="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95"
              enterTo="opacity-100 translate-y-0 sm:scale-100">
              <Dialog.Panel className={`rounded-3xl ${width} ${height}`}>
                {children}
              </Dialog.Panel>
            </Transition.Child>
          </div>
        </div>
      </Dialog>
    </Transition.Root>
  );
}

export default Modal;
