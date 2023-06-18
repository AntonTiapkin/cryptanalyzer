package com.javarush.cryptanalyser.tyapkin.applications;

import com.javarush.cryptanalyser.tyapkin.constants.cryptoAlphabet;
//import com.javarush.cryptanalyser.tyapkin.service.BrutForce;
import com.javarush.cryptanalyser.tyapkin.service.coding.Decode;
import com.javarush.cryptanalyser.tyapkin.service.coding.Encode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import static com.javarush.cryptanalyser.tyapkin.constants.guiConstants.*;

/**
 * Панель кодирования шифром цезаря.
 */
public class EncodePanel extends JPanel {


    /**
     * Ссылка на элемент который отвечает за ввод текста
     */

    private static JTextField inputTextField;

    /**
     * Ссылка на элемент который отвечает за ввод ключа
     */

    private JTextField inputKeyTextField;

    /**
     * Ссылка на элемент который отвечает за вывод текста
     */

    private static JTextField outputTextField;

    /**
     * Ссылка на элемент который отвечает за вывод текста по умолчанию
     */
    private JCheckBox enterDefaultParameter;


    public EncodePanel() {
        super();
        // Устанавливаем лэваут коробкой по вертикальной оси
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        // Размер панели
        setSize(600, 300);
        GridBagConstraints c = new GridBagConstraints();

        // инициализируем
        init();
    }

    /**
     * Инициализация панели шифрования текста
     */

    private void init() {
        // Создаем заголовок для поля ввода текста
        JLabel inputTextLabel = new JLabel(INPUT_TEXT);
        add(inputTextLabel);


        // Создаем поле ввода текста
        inputTextField = new JTextField();
        add(inputTextField);

        // Создаем заголовок для поля ввода ключа
        JLabel inputKeyTextLabel = new JLabel(INPUT_KEY_TEXT);
        add(inputKeyTextLabel);

        // Создаем поле ввода ключа
        inputKeyTextField = new JTextField();
        inputKeyTextField.setSize(50, 50);
        add(inputKeyTextField);

        // Создаем заголовок для поля вывода шифра
        JLabel outputTextLabel = new JLabel(OUTPUT_TEXT);
        outputTextLabel.setSize(50, 50);
        add(outputTextLabel);

        // Создаем поле вывода шифра
        outputTextField = new JTextField();
        outputTextField.setEnabled(false);
        add(outputTextField);

        // Создаем чекбокс
        enterDefaultParameter = new JCheckBox(CHECK_BOX_TEXT);
        enterDefaultParameter.addItemListener(new BruteForceButtonActionListener.EnterCheckBox());
        enterDefaultParameter.setEnabled(true);
        enterDefaultParameter.setForeground(Color.RED);
        add(enterDefaultParameter);


        // Создаем Кнопку зашифровать
        JButton encodeButton = new JButton(ENCODE_BUTTON_TEXT);
        encodeButton.addActionListener(new EncodeButtonActionListener());
        add(encodeButton);

        // Создаем Кнопку расшифровать
        JButton decodeButton = new JButton(DECODE_BUTTON_TEXT);
        decodeButton.addActionListener(new DecodeButtonActionListener());
        add(decodeButton);

        // Создаем Кнопку BruteForce
        JButton brutForceButton = new JButton(BRUTE_FORCE_BUTTON_TEXT);
        encodeButton.addActionListener(new BruteForceButtonActionListener());
        add(brutForceButton);


    }


    /**
     * Обработчик нажатия на кнопку.
     */

    class EncodeButtonActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            // генератор случайных чисел
            final Random rng = new Random();
            // забираем текст для шифрования
            final String textToEncode = inputTextField.getText();
            // забираем текст ключа шифрования
            final String encryptionKeyText = inputKeyTextField.getText();

            int encryptionKey;


            // если текст ключа шифрования пустой, то берем случайный ключ
            if (encryptionKeyText.isEmpty()) {
                encryptionKey = rng.nextInt(cryptoAlphabet.ALPHABET.length() - 1);
                inputKeyTextField.setText(String.valueOf(encryptionKey));
            } else {
                // если текст не пустой пытаемся первый элемент текста превратить в целое число, если не получается, берем случайный ключ
                try {
                    encryptionKey = Integer.parseInt(encryptionKeyText.substring(0, 1));
                } catch (NumberFormatException nfeIgnored) {
                    encryptionKey = rng.nextInt(cryptoAlphabet.ALPHABET.length() - 1);
                    inputKeyTextField.setText(String.valueOf(encryptionKey));
                }
            }
            // Проводим шифрование и выставляем результат в поле вывода текста
            outputTextField.setText(Encode.encrypt(textToEncode, encryptionKey));

        }
    }

    /**
     * Обработчик нажатия на кнопку.
     */


    class DecodeButtonActionListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            // генератор случайных чисел
            final Random rng = new Random();
            // забираем текст для шифрования
            final String textToDecode = inputTextField.getText();
            // забираем текст ключа шифрования
            final String decryptionKeyText = inputKeyTextField.getText();
            int decryptionKey;
            // если текст ключа шифрования пустой, то берем случайный ключ
            if (decryptionKeyText.isEmpty()) {
                decryptionKey = rng.nextInt(cryptoAlphabet.ALPHABET.length() - 1);
                inputKeyTextField.setText(String.valueOf(decryptionKey));
            } else {
                // если текст не пустой пытаемся первый элемент текста превратить в целое число, если не получается, берем случайный ключ
                try {
                    decryptionKey = Integer.parseInt(decryptionKeyText.substring(0, 1));
                } catch (NumberFormatException nfeIgnored) {
                    decryptionKey = rng.nextInt(cryptoAlphabet.ALPHABET.length() - 1);
                    inputKeyTextField.setText(String.valueOf(decryptionKey));
                }
            }
            // Проводим шифрование и выставляем результат в поле вывода текста
            outputTextField.setText(Decode.decrypt(textToDecode, decryptionKey));

        }
    }

    /**
     * Обработчик нажатия на кнопку.
     */

    class BruteForceButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // забираем текст для шифрования
            final String textToDecode = inputTextField.getText();

            // Проводим шифрование и выставляем результат в поле вывода текста

            try {
                outputTextField.setText(Decode.BrutForceDecode(textToDecode));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        /**
         * Обработчик нажатия на checkBox
         */


        static class EnterCheckBox implements ItemListener {


            @Override

            public void itemStateChanged(ItemEvent e) {


                if (e.getStateChange() == 1) {


                }


            }

        }
    }
}


