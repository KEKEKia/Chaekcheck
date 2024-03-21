import { createContext, useContext, useState, ReactNode, useMemo } from 'react';

interface ModalClassType {
  modalOpen: { [key: string]: boolean };
  openModal: (modalName: string) => void;
  closeModal: (modalName: string) => void;
}

const ModalContext = createContext<ModalClassType>({
  modalOpen: {},
  openModal: () => {},
  closeModal: () => {},
});

interface ModalProviderProps {
  children: ReactNode;
}

export function ModalProvider({ children }: ModalProviderProps) {
  const [modalOpen, setModalOpen] = useState<{ [key: string]: boolean }>({});

  const openModal = (modalName: string) => {
    setModalOpen(prevState => ({ ...prevState, [modalName]: true }));
  };

  const closeModal = (modalName: string) => {
    setModalOpen(prevState => ({ ...prevState, [modalName]: false }));
  };

  const contextValue = useMemo(
    () => ({ modalOpen, openModal, closeModal }),
    [modalOpen],
  );

  return (
    <ModalContext.Provider value={contextValue}>
      {children}
    </ModalContext.Provider>
  );
}

export const useModal = (): ModalClassType => {
  const context = useContext(ModalContext);

  if (!context) {
    throw new Error('useModal must be used within a ModalProvider');
  }

  return context;
};
