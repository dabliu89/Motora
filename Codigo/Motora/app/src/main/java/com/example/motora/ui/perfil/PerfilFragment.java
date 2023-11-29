package com.example.motora.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motora.LoginActivity;
import com.example.motora.databinding.ActivityUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.motora.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private static final String TAG = "ViewDatabase";

    private String nome, email, senha;
    private String emailU, senhaU;

    private FirebaseAuth mAuth;

    private FirebaseFirestore db;
    private StorageReference storageReference;

    private String key, papel;
    TextView campoNomeProfile;
    TextInputEditText campoNome, campoEmail, campoSenha;
    ImageView photo;

    Button atualizarDados, apagarConta;
    Uri imageUri;

    static boolean valido;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    private OnAccountDeletedListener accountDeletedListener;

    private FragmentPerfilBinding binding;

    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PerfilViewModel perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);

        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        //final TextView textView = binding.textPerfil;
        //perfilViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        photo = binding.imageProfile;
        campoNomeProfile = binding.textViewNomeProfile;
        campoNome = binding.editTextNome;
        campoEmail = binding.editTextEmail;
        campoSenha = binding.editTextSenha;

        atualizarDados = binding.buttonAtualizarDados;
        apagarConta = binding.buttonApagarConta;

        getUserData();

        user = FirebaseAuth.getInstance().getCurrentUser();

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mudarFoto();
            }
        });

        atualizarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmail();
            }
        });

        apagarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        return root;
    }

    private void getUserData() {
        Toast.makeText(getContext(), "Espere os seus dados carregarem...", Toast.LENGTH_SHORT).show();

        key = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Usuario").document(key).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                nome = documentSnapshot.getString("nome");
                                email = documentSnapshot.getString("email");
                                senha = documentSnapshot.getString("senha");
                                papel = documentSnapshot.getString("papel");

                                campoNomeProfile.setText(documentSnapshot.getString("nome"));
                                campoNome.setText(documentSnapshot.getString("nome"));
                                campoEmail.setText(documentSnapshot.getString("email"));
                                campoSenha.setText(documentSnapshot.getString("senha"));

                                if (papel.equals("Aluno") || papel.equals("aluno")) {
                                    // Diferenças de campos que aparecem para alunos
                                } else {
                                    // Diferenças de campos que aparecem para professores
                                }

                                try {
                                    getUserProfile();
                                } catch (IOException e) {
                                    Toast.makeText(getContext(), "Usuário sem imagem ou com falha em carregar. No segundo caso, erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUserProfile() throws IOException {
        storageReference = FirebaseStorage.getInstance().getReference("Usuario/" + key + ".jpg");

        File localFile = File.createTempFile("tempImage", "jpg");

        storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());

                RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                roundedDrawable.setCircular(true);
                photo.setBackground(roundedDrawable); // Define a forma redonda como plano de fundo
                photo.setImageDrawable(roundedDrawable);

                //photo.setImageBitmap(bitmap);
                photo.setRotation(getCameraPhotoOrientation(localFile.getAbsolutePath()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Falha em carregar imagem ou perfil não possui uma foto definida", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void mudarFoto() {

        if (checkAndRequestPermissions(getContext(), getActivity())) {
            chooseImage();
        }

    }

    public static boolean checkAndRequestPermissions(Context context, Activity activity) {
        int wExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (wExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),
                                    "É necessário permissão para usar a câmera.", Toast.LENGTH_SHORT)
                            .show();
                } else if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(),
                            "É necessário permissão para acessar arquivos.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    chooseImage();
                }
                break;
        }
    }

    private void chooseImage() {
        final CharSequence[] optionsMenu = {"Tirar Foto", "Escolher na Galeria", "Sair"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Tirar Foto")) {

                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Escolher na Galeria")) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Sair")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        storageReference = FirebaseStorage.getInstance().getReference("Usuario/" + key + ".jpg");

        if (data != null) {
            Log.d(String.valueOf(getActivity()), "Data: " + data.getData());

            if (resultCode != Activity.RESULT_CANCELED) {
                if (data != null) {
                    switch (requestCode) {
                        case 0:
                            if (resultCode == Activity.RESULT_OK && data != null) {
                                Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                                Matrix mat = new Matrix();
                                mat.postRotate(0);

                                Bitmap selectedImageRotate = Bitmap.createBitmap(selectedImage, 0, 0, selectedImage.getWidth(), selectedImage.getHeight(), mat, true);

                                photo.setImageBitmap(selectedImageRotate);

                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                selectedImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                                byte bb[] = bytes.toByteArray();

                                storageReference.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getContext(), "Imagem de perfil salva com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            break;
                        case 1:
                            if (resultCode == Activity.RESULT_OK && data != null) {
                                Uri selectedImage = data.getData();
                                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                                if (selectedImage != null) {
                                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                                    if (cursor != null) {

                                        cursor.moveToFirst();
                                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                        String picturePath = cursor.getString(columnIndex);

                                        photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                        photo.setRotation(getCameraPhotoOrientation(picturePath));

                                        imageUri = data.getData();

                                        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Toast.makeText(getContext(), "Image salva no banco com sucesso", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), "Falha em salvar imagem no banco", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        cursor.close();
                                    }
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    public static int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imagePath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 90;
                    break;
                default:
                    rotate = 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private void updateEmail() {
        emailU = Objects.requireNonNull(campoEmail.getText()).toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Objects.requireNonNull(user).updateEmail(emailU).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Email atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    update();
                } else {
                    Toast.makeText(getContext(), "Ocorreu um erro em atualizar o email, faça login novamente e tente de novo.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(), "Task: " + task.getException(), Toast.LENGTH_LONG).show();
                    Log.d(String.valueOf(getActivity()),"Task: " + task.getException());
                }
            }
        });
    }

    public void update() {
        nome = Objects.requireNonNull(campoNome.getText()).toString();

        if (verEmail()) {
            if (verNome()) {

                db.collection("Usuario").document(key)
                        .update("nome", nome, "email", emailU)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Dados atualizados", Toast.LENGTH_SHORT).show();
                                updateSenha();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        }
    }

    public void updateSenha() {
        senhaU = Objects.requireNonNull(campoSenha.getText()).toString();

        Toast.makeText(getContext(), "Senha: " + senhaU, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Objects.requireNonNull(user).updatePassword(senhaU).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    atualizarSenhaNoBanco();

                    Toast.makeText(getContext(), "Senha atualizada com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Ocorreu um erro ao atualizar a senha, tente fazer login de novo e tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void atualizarSenhaNoBanco() {
        db.collection("Usuario").document(key)
                .update("senha", senhaU)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Banco de dados atualizado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public interface OnAccountDeletedListener {
        void onAccountDeleted();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            accountDeletedListener = (OnAccountDeletedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " deve implementar OnAccountDeletedListener");
        }
    }

    public void delete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Tem certeza de que deseja excluir sua conta?");
        builder.setCancelable(false);

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                apagarContaFirestore();
            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    private void apagarContaFirestore() {
        db.collection("Usuario").document(key)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Conta excluída com sucesso", Toast.LENGTH_SHORT).show();
                        apagarContaAuthentication();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Usuario")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Conta deletada com sucesso");

                                            // Você pode adicionar código aqui para navegar para outra tela ou fazer outra ação após a exclusão da conta

                                            accountDeletedListener.onAccountDeleted();
                                        } else {
                                            Log.d(TAG, "Erro em deletar");
                                        }
                                    }
                                });
                    }
                });
    }

    private void apagarContaAuthentication() {

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("Usuario")
                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Conta deletada com sucesso");

                                            // Você pode adicionar código aqui para navegar para outra tela ou fazer outra ação após a exclusão da conta

                                            accountDeletedListener.onAccountDeleted();
                                        } else {
                                            Log.d(TAG, "Erro em deletar");
                                        }
                                    }
                                });
                    }
                });
    }

    private boolean verEmail() {

        if (emailU.equals(email)) {
            valido = true;
        } else {
            if (conteudoEmail()) {
                valido = true;
            } else {
                Toast.makeText(getContext(), "Email inválido", Toast.LENGTH_SHORT).show();
                valido = false;
            }
        }

        return valido;
    }

    private boolean conteudoEmail() {
        int numTiposContas = 0;

        if (emailU.contains("@gmail.com")) {
            ++numTiposContas;
        }

        if (emailU.contains("@alu.ufc.br")) {
            ++numTiposContas;
        }

        if (emailU.contains("@hotmail.com")) {
            ++numTiposContas;
        }

        if (numTiposContas > 1 || emailU.contains(" ")) {
            return false;
        } else {
            StringBuilder ultimasLetras = new StringBuilder();

            for (int i = emailU.length() - 12; i < emailU.length(); ++i) {
                char ch = emailU.charAt(i);
                ultimasLetras.append(ch);
            }

            return ultimasLetras.toString().contains("@gmail.com") || ultimasLetras.toString().contains("@alu.ufc.br") || ultimasLetras.toString().contains("@hotmail.com");
        }
    }

    private boolean verNome() {
        for (int i = 0; i < nome.length(); ++i) {
            char ch = nome.charAt(i);

            if (i < nome.length() - 1) {
                char ch1 = nome.charAt(i + 1);

                if (ch == ' ' && ch1 == ' ') {
                    return false;
                }
            }
        }

        if (nome.length() == 0) {
            return false;
        } else {
            for (int i = 0; i < nome.length(); ++i) {
                char ch = nome.charAt(i);

                if (!((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= 'á' && ch <= 'ú') || (ch >= 'Á' && ch <= 'Ú') || ch == 'ã' || ch == 'õ' || ch == ' ') || (i == 0 && ch == ' ')) {
                    Toast.makeText(getContext(), "Preencha o campo Nome apropriadamente (Verifique espaços indesejados)", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}